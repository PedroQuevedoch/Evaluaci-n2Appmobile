package com.example.dulzurasyasna.data.repository

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * Resultado de las operaciones de autenticación
 */
sealed class AuthResult {
    data class Success(val user: FirebaseUser) : AuthResult()
    data class Error(val message: String) : AuthResult()
}

/**
 * Repositorio para manejar operaciones de usuarios con Firebase Authentication
 */
class UserRepository {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    /**
     * Crea un nuevo usuario en Firebase Authentication
     * También guarda información adicional en Firestore (opcional, si falla no bloquea la creación)
     */
    suspend fun createUser(
        email: String,
        password: String,
        name: String,
        username: String
    ): AuthResult {
        return try {
            // Crear usuario en Firebase Authentication (esto es lo único requerido)
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user ?: return AuthResult.Error("Error al crear usuario")

            // Actualizar perfil con el nombre
            try {
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build()
                user.updateProfile(profileUpdates).await()
            } catch (e: Exception) {
                // Si falla actualizar el perfil, continuamos de todas formas
                // El usuario ya fue creado exitosamente
            }

            // Intentar guardar información adicional en Firestore (opcional)
            // Si falla, no bloqueamos la creación del usuario
            try {
                val userData = hashMapOf(
                    "name" to name,
                    "email" to email,
                    "username" to username,
                    "createdAt" to Timestamp.now()
                )

                firestore.collection("users")
                    .document(user.uid)
                    .set(userData)
                    .await()
            } catch (e: Exception) {
                // Si Firestore falla (por permisos o conexión), ignoramos el error
                // El usuario ya fue creado en Firebase Auth, que es lo importante
                // Los datos adicionales se pueden guardar más tarde
            }

            AuthResult.Success(user)
        } catch (e: Exception) {
            AuthResult.Error(getErrorMessage(e))
        }
    }

    /**
     * Inicia sesión con email y contraseña
     */
    suspend fun signIn(email: String, password: String): AuthResult {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user
            if (user != null) {
                AuthResult.Success(user)
            } else {
                AuthResult.Error("Error al iniciar sesión")
            }
        } catch (e: Exception) {
            AuthResult.Error(getErrorMessage(e))
        }
    }

    /**
     * Inicia sesión con username o email
     * Primero intenta como email, si falla intenta buscar el username en Firestore
     */
    suspend fun signInWithUsername(username: String, password: String): AuthResult {
        return try {
            // Primero intentar iniciar sesión directamente como email
            // Esto funciona si el usuario ingresó su email en el campo username
            val emailResult = try {
                signIn(username, password)
            } catch (e: Exception) {
                null
            }
            
            // Si funcionó como email, retornar el resultado
            if (emailResult is AuthResult.Success) {
                return emailResult
            }
            
            // Si no funcionó, intentar buscar el username en Firestore
            try {
                val querySnapshot = firestore.collection("users")
                    .whereEqualTo("username", username)
                    .limit(1)
                    .get()
                    .await()

                if (querySnapshot.isEmpty) {
                    return AuthResult.Error("Usuario o contraseña incorrectos")
                }

                val document = querySnapshot.documents.first()
                val email = document.getString("email") 
                    ?: return AuthResult.Error("Email no encontrado para este usuario")

                // Iniciar sesión con el email encontrado
                signIn(email, password)
            } catch (e: Exception) {
                // Si Firestore no está disponible o hay error, intentar como email otra vez
                // o mostrar error genérico
                AuthResult.Error("Usuario o contraseña incorrectos. Intenta con tu email.")
            }
        } catch (e: Exception) {
            AuthResult.Error(getErrorMessage(e))
        }
    }

    /**
     * Obtiene el usuario actual autenticado
     */
    fun getCurrentUser(): FirebaseUser? = auth.currentUser

    /**
     * Cierra sesión
     */
    fun signOut() {
        auth.signOut()
    }

    /**
     * Convierte excepciones de Firebase a mensajes de error en español
     */
    private fun getErrorMessage(exception: Exception): String {
        val errorMessage = exception.message ?: exception.localizedMessage ?: ""
        
        return when {
            errorMessage.contains("email address is already in use", ignoreCase = true) ||
            errorMessage.contains("already exists", ignoreCase = true) ||
            errorMessage.contains("EMAIL_EXISTS", ignoreCase = true) -> 
                "Este correo electrónico ya está registrado. Usa otro correo o intenta iniciar sesión."
            
            errorMessage.contains("password is too weak", ignoreCase = true) ||
            errorMessage.contains("WEAK_PASSWORD", ignoreCase = true) -> 
                "La contraseña es muy débil. Debe tener al menos 6 caracteres"
            
            errorMessage.contains("email address is badly formatted", ignoreCase = true) ||
            errorMessage.contains("INVALID_EMAIL", ignoreCase = true) -> 
                "El formato del correo electrónico no es válido"
            
            errorMessage.contains("no user record", ignoreCase = true) ||
            errorMessage.contains("USER_NOT_FOUND", ignoreCase = true) ||
            errorMessage.contains("no user", ignoreCase = true) -> 
                "Usuario no encontrado. Verifica tu email o crea una cuenta."
            
            errorMessage.contains("password is invalid", ignoreCase = true) ||
            errorMessage.contains("wrong password", ignoreCase = true) ||
            errorMessage.contains("INVALID_PASSWORD", ignoreCase = true) -> 
                "Contraseña incorrecta. Verifica tu contraseña e intenta nuevamente."
            
            errorMessage.contains("network error", ignoreCase = true) ||
            errorMessage.contains("timeout", ignoreCase = true) ||
            errorMessage.contains("unreachable", ignoreCase = true) ||
            errorMessage.contains("NETWORK_ERROR", ignoreCase = true) -> 
                "Error de conexión. Verifica tu internet e intenta nuevamente."
            
            errorMessage.contains("permission", ignoreCase = true) ||
            errorMessage.contains("PERMISSION_DENIED", ignoreCase = true) -> 
                "Error de permisos. Verifica la configuración de Firebase."
            
            else -> "Error: ${errorMessage.take(100)}"
        }
    }
}


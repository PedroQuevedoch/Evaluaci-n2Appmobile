package com.example.dulzurasyasna.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.dulzurasyasna.data.repository.UserRepository
import com.example.dulzurasyasna.data.repository.AuthResult

data class SignUpState(
    val name: String = "",
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val nameError: String? = null,
    val emailError: String? = null,
    val usernameError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val isSignUpEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val signUpError: String? = null,
    val signUpSuccess: Boolean = false
)

class SignUpViewModel(application: Application) : AndroidViewModel(application) {
    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state.asStateFlow()
    
    private val userRepository = UserRepository()

    fun updateName(name: String) {
        _state.update { current ->
            val error = validateName(name)
            current.copy(
                name = name,
                nameError = error,
                signUpError = null,
                isSignUpEnabled = validateSignUp(name, current.email, current.username, current.password, current.confirmPassword) && !current.isLoading
            )
        }
    }

    fun updateEmail(email: String) {
        val error = validateEmail(email)
        _state.update { current ->
            current.copy(
                email = email,
                emailError = error,
                signUpError = null,
                isSignUpEnabled = validateSignUp(current.name, email, current.username, current.password, current.confirmPassword) && !current.isLoading
            )
        }
        // No verificamos disponibilidad en tiempo real - Firebase Auth manejará el error si el email ya existe
    }

    fun updateUsername(username: String) {
        val error = validateUsername(username)
        _state.update { current ->
            current.copy(
                username = username,
                usernameError = error,
                signUpError = null,
                isSignUpEnabled = validateSignUp(current.name, current.email, username, current.password, current.confirmPassword) && !current.isLoading
            )
        }
        // No verificamos disponibilidad en tiempo real - Firebase Auth manejará el error si el email ya existe
    }

    fun updatePassword(password: String) {
        _state.update { current ->
            val error = validatePassword(password)
            val confirmError = if (current.confirmPassword.isNotEmpty()) {
                validateConfirmPassword(password, current.confirmPassword)
            } else null
            current.copy(
                password = password,
                passwordError = error,
                confirmPasswordError = confirmError,
                signUpError = null,
                isSignUpEnabled = validateSignUp(current.name, current.email, current.username, password, current.confirmPassword) && !current.isLoading
            )
        }
    }

    fun updateConfirmPassword(confirmPassword: String) {
        _state.update { current ->
            val error = validateConfirmPassword(current.password, confirmPassword)
            current.copy(
                confirmPassword = confirmPassword,
                confirmPasswordError = error,
                signUpError = null,
                isSignUpEnabled = validateSignUp(current.name, current.email, current.username, current.password, confirmPassword) && !current.isLoading
            )
        }
    }

    /**
     * Crea un nuevo usuario en Firebase
     */
    fun signUp(onSuccess: () -> Unit) {
        val current = _state.value
        
        // Validar todos los campos antes de intentar crear el usuario
        if (!validateSignUp()) {
            return
        }
        
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, signUpError = null, signUpSuccess = false) }
            
            val result = userRepository.createUser(
                email = current.email,
                password = current.password,
                name = current.name,
                username = current.username
            )
            
            when (result) {
                is AuthResult.Success -> {
                    _state.update { 
                        it.copy(
                            isLoading = false,
                            signUpSuccess = true,
                            signUpError = null
                        )
                    }
                    onSuccess()
                }
                is AuthResult.Error -> {
                    _state.update { 
                        it.copy(
                            isLoading = false,
                            signUpError = result.message,
                            signUpSuccess = false
                        )
                    }
                }
            }
        }
    }

    // Eliminadas las verificaciones de disponibilidad - Firebase Auth manejará los errores directamente

    fun validateSignUp(): Boolean {
        val current = _state.value
        val nameError = validateName(current.name)
        val emailError = validateEmail(current.email)
        val usernameError = validateUsername(current.username)
        val passwordError = validatePassword(current.password)
        val confirmPasswordError = validateConfirmPassword(current.password, current.confirmPassword)
        
        _state.update {
            it.copy(
                nameError = nameError,
                emailError = emailError,
                usernameError = usernameError,
                passwordError = passwordError,
                confirmPasswordError = confirmPasswordError,
                isSignUpEnabled = nameError == null && emailError == null && 
                                 usernameError == null && passwordError == null && 
                                 confirmPasswordError == null && !it.isLoading
            )
        }
        return _state.value.isSignUpEnabled
    }

    private fun validateName(name: String): String? {
        return when {
            name.isBlank() -> "El nombre es requerido"
            name.length < 2 -> "El nombre debe tener al menos 2 caracteres"
            else -> null
        }
    }

    private fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> "El correo es requerido"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "El correo no es válido"
            else -> null
        }
    }

    private fun validateUsername(username: String): String? {
        return when {
            username.isBlank() -> "El usuario es requerido"
            username.length < 3 -> "El usuario debe tener al menos 3 caracteres"
            else -> null
        }
    }

    private fun validatePassword(password: String): String? {
        return when {
            password.isBlank() -> "La contraseña es requerida"
            password.length < 6 -> "La contraseña debe tener al menos 6 caracteres"
            else -> null
        }
    }

    private fun validateConfirmPassword(password: String, confirmPassword: String): String? {
        return when {
            confirmPassword.isBlank() -> "Confirme la contraseña"
            password != confirmPassword -> "Las contraseñas no coinciden"
            else -> null
        }
    }

    private fun validateSignUp(
        name: String,
        email: String,
        username: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        return validateName(name) == null &&
               validateEmail(email) == null &&
               validateUsername(username) == null &&
               validatePassword(password) == null &&
               validateConfirmPassword(password, confirmPassword) == null
    }
}




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

data class LoginState(
    val username: String = "",
    val password: String = "",
    val usernameError: String? = null,
    val passwordError: String? = null,
    val isLoginEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val loginError: String? = null
)

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()
    
    private val userRepository = UserRepository()

    fun updateUsername(username: String) {
        _state.update { current ->
            val error = validateUsername(username)
            current.copy(
                username = username,
                usernameError = error,
                loginError = null,
                isLoginEnabled = validateLogin(username, current.password) && !current.isLoading
            )
        }
    }

    fun updatePassword(password: String) {
        _state.update { current ->
            val error = validatePassword(password)
            current.copy(
                password = password,
                passwordError = error,
                loginError = null,
                isLoginEnabled = validateLogin(current.username, password) && !current.isLoading
            )
        }
    }

    /**
     * Inicia sesión con username y contraseña usando Firebase
     */
    fun login(onSuccess: () -> Unit) {
        val current = _state.value
        
        // Validar campos antes de intentar login
        if (!validateLogin()) {
            return
        }
        
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, loginError = null) }
            
            val result = userRepository.signInWithUsername(
                username = current.username,
                password = current.password
            )
            
            when (result) {
                is AuthResult.Success -> {
                    _state.update { 
                        it.copy(
                            isLoading = false,
                            loginError = null
                        )
                    }
                    onSuccess()
                }
                is AuthResult.Error -> {
                    _state.update { 
                        it.copy(
                            isLoading = false,
                            loginError = result.message
                        )
                    }
                }
            }
        }
    }

    fun validateLogin(): Boolean {
        val current = _state.value
        val usernameError = validateUsername(current.username)
        val passwordError = validatePassword(current.password)
        
        _state.update {
            it.copy(
                usernameError = usernameError,
                passwordError = passwordError,
                isLoginEnabled = usernameError == null && passwordError == null && !it.isLoading
            )
        }
        return _state.value.isLoginEnabled
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

    private fun validateLogin(username: String, password: String): Boolean {
        return validateUsername(username) == null && validatePassword(password) == null
    }
}


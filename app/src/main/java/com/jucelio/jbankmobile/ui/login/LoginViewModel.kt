package com.jucelio.jbankmobile.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jucelio.jbankmobile.core.network.ApiResult
import com.jucelio.jbankmobile.data.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(
        LoginUiState()
    )
        private set

    fun onEvent(
        event: LoginEvent,
        onLoginSuccess: () -> Unit = {}
    ) {
        when (event) {
            is LoginEvent.EmailChanged -> {
                onEmailChange(event.email)
            }

            is LoginEvent.PasswordChanged -> {
                onPasswordChange(event.password)
            }

            is LoginEvent.RememberAccessChanged -> {
                onRememberAccessChange(
                    event.checked
                )
            }

            LoginEvent.TogglePasswordVisibility -> {
                togglePasswordVisibility()
            }

            LoginEvent.LoginClicked -> {
                login(onLoginSuccess)
            }

            LoginEvent.BiometricClicked -> {
                state = state.copy(
                    errorMessage =
                        "Autenticação biométrica ainda não configurada."
                )
            }

            LoginEvent.ForgotPasswordClicked -> {
                forgotPassword()
            }

            LoginEvent.ClearError -> {
                state = state.copy(
                    errorMessage = null
                )
            }
        }
    }

    fun onEmailChange(
        email: String
    ) {
        state = state.copy(
            email = email,
            errorMessage = null
        )
    }

    fun onPasswordChange(
        password: String
    ) {
        state = state.copy(
            password = password,
            errorMessage = null
        )
    }

    fun onRememberAccessChange(
        checked: Boolean
    ) {
        state = state.copy(
            rememberAccess = checked
        )
    }

    fun togglePasswordVisibility() {
        state = state.copy(
            isPasswordVisible =
                !state.isPasswordVisible
        )
    }

    fun forgotPassword() {
        state = state.copy(
            errorMessage =
                "Recuperação de senha ainda não configurada."
        )
    }

    fun login(
        onSuccess: () -> Unit
    ) {
        val email = state.email.trim()
        val password = state.password

        if (email.isBlank()) {
            state = state.copy(
                errorMessage =
                    "Informe o seu e-mail."
            )
            return
        }

        if (password.isBlank()) {
            state = state.copy(
                errorMessage =
                    "Informe a sua senha."
            )
            return
        }

        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                errorMessage = null
            )

            when (
                val result = repository.login(
                    email = email,
                    password = password
                )
            ) {
                is ApiResult.Success -> {
                    state = state.copy(
                        isLoading = false,
                        errorMessage = null
                    )

                    onSuccess()
                }

                is ApiResult.Error -> {
                    state = state.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }
            }
        }
    }
}

class LoginViewModelFactory(
    private val repository: AuthRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (
            modelClass.isAssignableFrom(
                LoginViewModel::class.java
            )
        ) {
            return LoginViewModel(
                repository = repository
            ) as T
        }

        throw IllegalArgumentException(
            "ViewModel desconhecido: ${modelClass.name}"
        )
    }
}
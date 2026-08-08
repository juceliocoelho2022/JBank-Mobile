package com.jucelio.jbankmobile.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jucelio.jbankmobile.core.network.ApiResult
import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Gerencia o estado e as ações da tela de autenticação.
 *
 * O repository é fornecido automaticamente pelo Hilt.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
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
                    checked = event.checked
                )
            }

            LoginEvent.TogglePasswordVisibility -> {
                togglePasswordVisibility()
            }

            LoginEvent.LoginClicked -> {
                login(
                    onSuccess = onLoginSuccess
                )
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
                clearError()
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

    fun clearError() {
        state = state.copy(
            errorMessage = null
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

        if (state.isLoading) {
            return
        }

        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                errorMessage = null
            )

            when (
                val result = loginUseCase(
                    email = email,
                    password = password
                )
            ) {
                is AppResult.Success -> {
                    state = state.copy(
                        isLoading = false,
                        errorMessage = null
                    )

                    onSuccess()
                }

                is AppResult.Failure -> {
                    state = state.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }
            }
        }
    }
}
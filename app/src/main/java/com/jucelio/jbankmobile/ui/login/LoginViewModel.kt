package com.jucelio.jbankmobile.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jucelio.jbankmobile.data.repository.AuthRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

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
                // A autenticação biométrica será
                // conectada posteriormente.
            }

            LoginEvent.ForgotPasswordClicked -> {
                state = state.copy(
                    errorMessage =
                        "Recuperação de senha ainda não configurada."
                )
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
        if (state.email.isBlank()) {
            state = state.copy(
                errorMessage =
                    "Informe o seu e-mail."
            )
            return
        }

        if (state.password.isBlank()) {
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

            repository.login(
                email = state.email.trim(),
                password = state.password
            ).onSuccess {
                state = state.copy(
                    isLoading = false,
                    errorMessage = null
                )

                onSuccess()
            }.onFailure { error ->
                state = state.copy(
                    isLoading = false,
                    errorMessage =
                        error.toLoginMessage()
                )
            }
        }
    }
}

private fun Throwable.toLoginMessage(): String {
    return when (this) {
        is HttpException -> when (code()) {
            400 ->
                "Dados de login inválidos."

            401, 403 ->
                "E-mail ou senha inválidos."

            404 ->
                "Usuário não encontrado."

            500 ->
                "Erro interno no servidor."

            else ->
                "Erro HTTP ${code()} ao realizar o login."
        }

        is IOException ->
            "Não foi possível conectar à API. Verifique se o backend está rodando."

        else ->
            message ?: "Não foi possível realizar o login."
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
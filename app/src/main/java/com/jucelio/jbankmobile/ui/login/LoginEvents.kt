package com.jucelio.jbankmobile.ui.login

sealed interface LoginEvent {

    data class EmailChanged(
        val email: String
    ) : LoginEvent

    data class PasswordChanged(
        val password: String
    ) : LoginEvent

    data class RememberAccessChanged(
        val checked: Boolean
    ) : LoginEvent

    data object TogglePasswordVisibility : LoginEvent

    data object LoginClicked : LoginEvent

    data object BiometricClicked : LoginEvent

    data object ForgotPasswordClicked : LoginEvent

    data object ClearError : LoginEvent
}
package com.jucelio.jbankmobile.ui.auth.access

sealed interface AccessEvent {

    data class SelectMethod(
        val method: AccessMethod
    ) : AccessEvent

    data class EmailChanged(
        val value: String
    ) : AccessEvent

    data class PinChanged(
        val value: String
    ) : AccessEvent

    data class PasswordChanged(
        val value: String
    ) : AccessEvent

    data object TogglePasswordVisibility : AccessEvent

    data object AuthenticateWithBiometric : AccessEvent

    data object AuthenticateWithPin : AccessEvent

    data object AuthenticateWithPassword : AccessEvent

    data object ClearError : AccessEvent
}
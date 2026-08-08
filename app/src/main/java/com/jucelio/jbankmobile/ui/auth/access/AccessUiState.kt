package com.jucelio.jbankmobile.ui.auth.access

enum class AccessMethod {
    BIOMETRIC,
    PIN,
    PASSWORD
}

data class AccessUiState(
    val userName: String = "Jucelio",
    val email: String = "",
    val selectedMethod: AccessMethod =
        AccessMethod.BIOMETRIC,
    val pin: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val biometricAvailable: Boolean = true,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val authenticationSuccessful: Boolean = false
)
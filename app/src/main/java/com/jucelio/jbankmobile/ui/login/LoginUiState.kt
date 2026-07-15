package com.jucelio.jbankmobile.ui.login

data class LoginUiState(
    val email: String = "admin@jbank.com",
    val password: String = "",
    val rememberAccess: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    val canLogin: Boolean
        get() = email.isNotBlank() &&
                password.isNotBlank() &&
                !isLoading
}
package com.jucelio.jbankmobile.feature.auth.presentation.state

import com.jucelio.jbankmobile.core.common.UiState

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
) : UiState
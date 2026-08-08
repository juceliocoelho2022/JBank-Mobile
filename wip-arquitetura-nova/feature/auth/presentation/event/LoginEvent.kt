package com.jucelio.jbankmobile.feature.auth.presentation.event

import com.jucelio.jbankmobile.core.common.UiEvent

sealed interface LoginEvent {

    data class EmailChanged(
        val value: String
    ) : LoginEvent

    data class PasswordChanged(
        val value: String
    ) : LoginEvent

    data object LoginClicked : LoginEvent
}
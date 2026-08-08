package com.jucelio.jbankmobile.feature.auth.presentation.effect

import com.jucelio.jbankmobile.core.common.UiEffect

sealed interface LoginEffect : UiEffect {

    data object NavigateHome : LoginEffect

    data class ShowError(
        val message: String
    ) : LoginEffect
}
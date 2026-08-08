package com.jucelio.jbankmobile.ui.welcome

sealed interface WelcomeEvent {

    data object AccessAccountClicked : WelcomeEvent

    data object OpenAccountClicked : WelcomeEvent

    data object ClearError : WelcomeEvent
}
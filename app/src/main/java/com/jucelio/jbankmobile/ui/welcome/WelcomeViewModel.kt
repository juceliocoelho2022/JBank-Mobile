package com.jucelio.jbankmobile.ui.welcome

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WelcomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        WelcomeUiState()
    )

    val uiState: StateFlow<WelcomeUiState> =
        _uiState.asStateFlow()

    private val _navigationEvent =
        MutableSharedFlow<WelcomeNavigationEvent>()

    val navigationEvent: SharedFlow<WelcomeNavigationEvent> =
        _navigationEvent.asSharedFlow()

    fun onEvent(event: WelcomeEvent) {
        when (event) {
            WelcomeEvent.AccessAccountClicked -> {
                navigateToAccountIdentification()
            }

            WelcomeEvent.OpenAccountClicked -> {
                navigateToOpenAccount()
            }

            WelcomeEvent.ClearError -> {
                clearError()
            }
        }
    }

    private fun navigateToAccountIdentification() {
        _navigationEvent.tryEmit(
            WelcomeNavigationEvent.AccountIdentification
        )
    }

    private fun navigateToOpenAccount() {
        _navigationEvent.tryEmit(
            WelcomeNavigationEvent.OpenAccount
        )
    }

    private fun clearError() {
        _uiState.update {
            it.copy(
                errorMessage = null
            )
        }
    }
}

sealed interface WelcomeNavigationEvent {

    data object AccountIdentification :
        WelcomeNavigationEvent

    data object OpenAccount :
        WelcomeNavigationEvent
}
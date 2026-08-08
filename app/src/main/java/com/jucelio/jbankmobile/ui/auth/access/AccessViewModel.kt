package com.jucelio.jbankmobile.ui.auth.access

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jucelio.jbankmobile.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccessViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state =
        MutableStateFlow(AccessUiState())

    val state: StateFlow<AccessUiState> =
        _state.asStateFlow()

    fun onEvent(event: AccessEvent) {
        when (event) {
            is AccessEvent.SelectMethod -> {
                selectMethod(event.method)
            }

            is AccessEvent.EmailChanged -> {
                updateEmail(event.value)
            }

            is AccessEvent.PinChanged -> {
                updatePin(event.value)
            }

            is AccessEvent.PasswordChanged -> {
                updatePassword(event.value)
            }

            AccessEvent.TogglePasswordVisibility -> {
                togglePasswordVisibility()
            }

            AccessEvent.AuthenticateWithBiometric -> {
                authenticateWithBiometric()
            }

            AccessEvent.AuthenticateWithPin -> {
                authenticateWithPin()
            }

            AccessEvent.AuthenticateWithPassword -> {
                authenticateWithPassword()
            }

            AccessEvent.ClearError -> {
                clearError()
            }
        }
    }

    private fun selectMethod(
        method: AccessMethod
    ) {
        _state.update {
            it.copy(
                selectedMethod = method,
                errorMessage = null
            )
        }
    }

    private fun updateEmail(
        value: String
    ) {
        _state.update {
            it.copy(
                email = value,
                errorMessage = null
            )
        }
    }

    private fun updatePin(
        value: String
    ) {
        val sanitizedPin = value
            .filter(Char::isDigit)
            .take(6)

        _state.update {
            it.copy(
                pin = sanitizedPin,
                errorMessage = null
            )
        }
    }

    private fun updatePassword(
        value: String
    ) {
        _state.update {
            it.copy(
                password = value,
                errorMessage = null
            )
        }
    }

    private fun togglePasswordVisibility() {
        _state.update {
            it.copy(
                passwordVisible =
                    !it.passwordVisible
            )
        }
    }

    private fun authenticateWithPassword() {
        val currentState = _state.value

        if (currentState.isLoading) {
            return
        }

        viewModelScope.launch {
            setLoading(true)

            when (
                val result = loginUseCase(
                    email = currentState.email,
                    password = currentState.password
                )
            ) {
                is AppResult.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            authenticationSuccessful = true,
                            errorMessage = null
                        )
                    }
                }

                is AppResult.Failure -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            authenticationSuccessful = false,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }

    private fun authenticateWithBiometric() {
        showError(
            "A biometria real será integrada na próxima etapa."
        )
    }

    private fun authenticateWithPin() {
        showError(
            "O login por PIN será integrado após o login por senha."
        )
    }

    private fun setLoading(
        isLoading: Boolean
    ) {
        _state.update {
            it.copy(
                isLoading = isLoading,
                errorMessage = null
            )
        }
    }

    private fun showError(
        message: String
    ) {
        _state.update {
            it.copy(
                isLoading = false,
                errorMessage = message
            )
        }
    }

    private fun clearError() {
        _state.update {
            it.copy(
                errorMessage = null
            )
        }
    }
}
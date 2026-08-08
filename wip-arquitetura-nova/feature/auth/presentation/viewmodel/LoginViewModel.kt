package com.jucelio.jbankmobile.feature.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jucelio.jbankmobile.core.common.Result
import com.jucelio.jbankmobile.domain.usecase.auth.LoginUseCase
import com.jucelio.jbankmobile.feature.auth.presentation.effect.LoginEffect
import com.jucelio.jbankmobile.feature.auth.presentation.event.LoginEvent
import com.jucelio.jbankmobile.feature.auth.presentation.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

    private val loginUseCase: LoginUseCase

) : ViewModel() {

    private val _state =
        MutableStateFlow(LoginState())

    val state: StateFlow<LoginState> =
        _state.asStateFlow()

    private val _effect =
        MutableSharedFlow<LoginEffect>()

    val effect: SharedFlow<LoginEffect> =
        _effect.asSharedFlow()

    fun onEvent(
        event: LoginEvent
    ) {

        when (event) {

            is LoginEvent.EmailChanged ->
                updateEmail(event.value)

            is LoginEvent.PasswordChanged ->
                updatePassword(event.value)

            LoginEvent.LoginClicked ->
                login()

        }

    }

    private fun updateEmail(
        email: String
    ) {

        _state.value =
            _state.value.copy(
                email = email
            )

    }

    private fun updatePassword(
        password: String
    ) {

        _state.value =
            _state.value.copy(
                password = password
            )

    }

    private fun login() {

        viewModelScope.launch {

            _state.value =
                _state.value.copy(
                    isLoading = true,
                    error = null
                )

            val result =
                loginUseCase(

                    LoginUseCase.Params(

                        email = state.value.email,

                        password = state.value.password

                    )

                )

            when (result) {

                is Result.Success -> {

                    _state.value =
                        _state.value.copy(
                            isLoading = false
                        )

                    _effect.emit(
                        LoginEffect.NavigateHome
                    )

                }

                is Result.Error -> {

                    _state.value =
                        _state.value.copy(

                            isLoading = false,

                            error = result.message

                        )

                    _effect.emit(

                        LoginEffect.ShowError(

                            result.message

                        )

                    )

                }

                Result.Loading -> {

                    _state.value =
                        _state.value.copy(
                            isLoading = true
                        )

                }

            }

        }

    }

}
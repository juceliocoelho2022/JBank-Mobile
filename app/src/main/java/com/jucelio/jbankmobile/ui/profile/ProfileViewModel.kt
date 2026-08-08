package com.jucelio.jbankmobile.ui.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.usecase.auth.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileLogoutState(
    val errorMessage: String? = null
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    var state by mutableStateOf(
        ProfileLogoutState()
    )
        private set

    fun logout(
        onDone: () -> Unit
    ) {
        viewModelScope.launch {
            when (
                val result = logoutUseCase()
            ) {
                is AppResult.Success -> {
                    onDone()
                }

                is AppResult.Failure -> {
                    state = state.copy(
                        errorMessage = result.message
                    )
                }
            }
        }
    }

    fun clearError() {
        state = state.copy(
            errorMessage = null
        )
    }
}

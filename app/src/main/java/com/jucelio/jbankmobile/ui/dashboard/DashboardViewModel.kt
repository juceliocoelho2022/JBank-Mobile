package com.jucelio.jbankmobile.ui.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.model.Dashboard
import com.jucelio.jbankmobile.domain.usecase.auth.LogoutUseCase
import com.jucelio.jbankmobile.domain.usecase.dashboard.GetDashboardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardUiState(
    val isLoading: Boolean = true,
    val data: Dashboard? = null,
    val errorMessage: String? = null
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getDashboardUseCase: GetDashboardUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    var state by mutableStateOf(
        DashboardUiState()
    )
        private set

    init {
        load()
    }

    fun load() {
        if (state.isLoading && state.data != null) {
            return
        }

        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                errorMessage = null
            )

            when (
                val result = getDashboardUseCase()
            ) {
                is AppResult.Success -> {
                    state = DashboardUiState(
                        isLoading = false,
                        data = result.data,
                        errorMessage = null
                    )
                }

                is AppResult.Failure -> {
                    state = DashboardUiState(
                        isLoading = false,
                        data = state.data,
                        errorMessage = result.message
                    )
                }
            }
        }
    }

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
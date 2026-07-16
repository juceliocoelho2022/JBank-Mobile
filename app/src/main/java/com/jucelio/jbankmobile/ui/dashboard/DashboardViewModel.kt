package com.jucelio.jbankmobile.ui.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jucelio.jbankmobile.data.remote.dto.DashboardResponseDto
import com.jucelio.jbankmobile.data.repository.AuthRepository
import com.jucelio.jbankmobile.data.repository.DashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

data class DashboardUiState(
    val isLoading: Boolean = true,
    val data: DashboardResponseDto? = null,
    val errorMessage: String? = null
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dashboardRepository: DashboardRepository,
    private val authRepository: AuthRepository
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

            dashboardRepository
                .loadDashboard()
                .onSuccess { dashboard ->
                    state = DashboardUiState(
                        isLoading = false,
                        data = dashboard,
                        errorMessage = null
                    )
                }
                .onFailure { error ->
                    state = DashboardUiState(
                        isLoading = false,
                        data = state.data,
                        errorMessage = error.toDashboardMessage()
                    )
                }
        }
    }

    fun logout(
        onDone: () -> Unit
    ) {
        viewModelScope.launch {
            runCatching {
                authRepository.logout()
            }.onSuccess {
                onDone()
            }.onFailure { error ->
                state = state.copy(
                    errorMessage = error.message
                        ?: "Não foi possível encerrar a sessão."
                )
            }
        }
    }

    fun clearError() {
        state = state.copy(
            errorMessage = null
        )
    }
}

private fun Throwable.toDashboardMessage(): String {
    return when (this) {
        is HttpException -> {
            when (code()) {
                401 ->
                    "Sua sessão expirou. Entre novamente."

                403 ->
                    "Você não possui permissão para acessar o dashboard."

                404 ->
                    "Os dados do dashboard não foram encontrados."

                in 500..599 ->
                    "O serviço está temporariamente indisponível."

                else ->
                    "Erro HTTP ${code()} ao carregar o dashboard."
            }
        }

        is IOException ->
            "Não foi possível conectar à API. Confirme se o backend está disponível."

        else ->
            message ?: "Não foi possível carregar o dashboard."
    }
}
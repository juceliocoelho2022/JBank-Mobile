package com.jucelio.jbankmobile.ui.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jucelio.jbankmobile.data.remote.dto.DashboardResponseDto
import com.jucelio.jbankmobile.data.repository.AuthRepository
import com.jucelio.jbankmobile.data.repository.DashboardRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

data class DashboardUiState(
    val isLoading: Boolean = true,
    val data: DashboardResponseDto? = null,
    val errorMessage: String? = null
)

class DashboardViewModel(
    private val repository: DashboardRepository
) : ViewModel() {

    var state by mutableStateOf(DashboardUiState())
        private set

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            state = state.copy(isLoading = true, errorMessage = null)

            repository.loadDashboard()
                .onSuccess {
                    state = DashboardUiState(
                        isLoading = false,
                        data = it
                    )
                }
                .onFailure { error ->
                    state = DashboardUiState(
                        isLoading = false,
                        errorMessage = when (error) {
                            is HttpException -> "Erro HTTP ${error.code()} ao carregar o dashboard."
                            is IOException -> "API indisponível. Confirme o backend na porta 8081."
                            else -> error.message ?: "Erro inesperado."
                        }
                    )
                }
        }
    }

    fun logout(repository: AuthRepository, onDone: () -> Unit) {
        viewModelScope.launch {
            repository.logout()
            onDone()
        }
    }
}

class DashboardViewModelFactory(
    private val repository: DashboardRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DashboardViewModel(repository) as T
    }
}

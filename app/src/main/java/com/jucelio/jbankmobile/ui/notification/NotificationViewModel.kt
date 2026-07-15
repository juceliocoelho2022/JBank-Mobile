package com.jucelio.jbankmobile.ui.notification

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jucelio.jbankmobile.data.remote.dto.NotificationResponseDto
import com.jucelio.jbankmobile.data.repository.NotificationRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

data class NotificationUiState(
    val isLoading: Boolean = true,
    val notifications: List<NotificationResponseDto> = emptyList(),
    val errorMessage: String? = null
)

class NotificationViewModel(
    private val repository: NotificationRepository
) : ViewModel() {

    var state by mutableStateOf(NotificationUiState())
        private set

    init {
        loadNotifications()
    }

    fun loadNotifications() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                errorMessage = null
            )

            repository.getNotifications()
                .onSuccess { notifications ->
                    state = NotificationUiState(
                        isLoading = false,
                        notifications = notifications,
                        errorMessage = null
                    )
                }
                .onFailure { error ->
                    state = NotificationUiState(
                        isLoading = false,
                        notifications = emptyList(),
                        errorMessage = error.toFriendlyMessage()
                    )
                }
        }
    }
}

private fun Throwable.toFriendlyMessage(): String {
    return when (this) {
        is HttpException -> when (code()) {
            401, 403 ->
                "Sua sessão expirou ou não possui autorização."

            404 ->
                "O endpoint de notificações não foi encontrado."

            else ->
                "Erro HTTP ${code()} ao carregar notificações."
        }

        is IOException ->
            "Não foi possível conectar à API na porta 8081."

        else ->
            message ?: "Erro inesperado ao carregar notificações."
    }
}

class NotificationViewModelFactory(
    private val repository: NotificationRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        return NotificationViewModel(repository) as T
    }
}
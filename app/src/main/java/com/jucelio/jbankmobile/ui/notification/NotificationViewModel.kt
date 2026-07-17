package com.jucelio.jbankmobile.ui.notification

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.model.Notification
import com.jucelio.jbankmobile.domain.usecase.notification.GetNotificationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NotificationUiState(
    val isLoading: Boolean = true,
    val notifications: List<Notification> = emptyList(),
    val errorMessage: String? = null
)

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getNotificationsUseCase:
    GetNotificationsUseCase
) : ViewModel() {

    var state by mutableStateOf(
        NotificationUiState()
    )
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

            when (
                val result = getNotificationsUseCase()
            ) {
                is AppResult.Success -> {
                    state = NotificationUiState(
                        isLoading = false,
                        notifications = result.data,
                        errorMessage = null
                    )
                }

                is AppResult.Failure -> {
                    state = NotificationUiState(
                        isLoading = false,
                        notifications = emptyList(),
                        errorMessage = result.message
                    )
                }
            }
        }
    }
}
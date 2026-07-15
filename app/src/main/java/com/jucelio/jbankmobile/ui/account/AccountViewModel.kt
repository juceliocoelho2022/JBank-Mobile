package com.jucelio.jbankmobile.ui.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jucelio.jbankmobile.data.remote.dto.AccountResponseDto
import com.jucelio.jbankmobile.data.repository.AccountRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

data class AccountUiState(
    val isLoading: Boolean = true,
    val accounts: List<AccountResponseDto> = emptyList(),
    val errorMessage: String? = null
)

class AccountViewModel(
    private val repository: AccountRepository
) : ViewModel() {

    var state by mutableStateOf(AccountUiState())
        private set

    init {
        loadAccounts()
    }

    fun loadAccounts() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                errorMessage = null
            )

            repository.getAccounts()
                .onSuccess { accounts ->
                    state = AccountUiState(
                        isLoading = false,
                        accounts = accounts,
                        errorMessage = null
                    )
                }
                .onFailure { error ->
                    state = AccountUiState(
                        isLoading = false,
                        accounts = emptyList(),
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
                "O endpoint de contas não foi encontrado."

            else ->
                "Erro HTTP ${code()} ao carregar as contas."
        }

        is IOException ->
            "Não foi possível conectar à API. Confirme o backend na porta 8081."

        else ->
            message ?: "Erro inesperado ao carregar as contas."
    }
}

class AccountViewModelFactory(
    private val repository: AccountRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        return AccountViewModel(repository) as T
    }
}
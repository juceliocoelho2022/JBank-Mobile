package com.jucelio.jbankmobile.ui.transaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jucelio.jbankmobile.data.remote.dto.TransactionResponseDto
import com.jucelio.jbankmobile.data.repository.TransactionRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

enum class TransactionFilter {
    ALL,
    INCOME,
    EXPENSE
}

data class TransactionUiState(
    val isLoading: Boolean = true,
    val transactions: List<TransactionResponseDto> = emptyList(),
    val filter: TransactionFilter = TransactionFilter.ALL,
    val errorMessage: String? = null
) {
    val filteredTransactions: List<TransactionResponseDto>
        get() = when (filter) {
            TransactionFilter.ALL -> transactions

            TransactionFilter.INCOME ->
                transactions.filter {
                    it.type?.uppercase() == "DEPOSIT"
                }

            TransactionFilter.EXPENSE ->
                transactions.filter {
                    it.type?.uppercase() != "DEPOSIT"
                }
        }
}

class TransactionViewModel(
    private val repository: TransactionRepository,
    private val accountId: Long
) : ViewModel() {

    var state by mutableStateOf(TransactionUiState())
        private set

    init {
        loadTransactions()
    }

    fun loadTransactions() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                errorMessage = null
            )

            repository.getStatement(accountId)
                .onSuccess { transactions ->
                    state = state.copy(
                        isLoading = false,
                        transactions = transactions,
                        errorMessage = null
                    )
                }
                .onFailure { error ->
                    state = state.copy(
                        isLoading = false,
                        transactions = emptyList(),
                        errorMessage = error.toFriendlyMessage()
                    )
                }
        }
    }

    fun changeFilter(filter: TransactionFilter) {
        state = state.copy(filter = filter)
    }
}

private fun Throwable.toFriendlyMessage(): String {
    return when (this) {
        is HttpException -> when (code()) {
            401, 403 ->
                "Sua sessão expirou ou não possui autorização."

            404 ->
                "O extrato da conta não foi encontrado."

            else ->
                "Erro HTTP ${code()} ao carregar as transações."
        }

        is IOException ->
            "Não foi possível conectar à API."

        else ->
            message ?: "Erro inesperado ao carregar as transações."
    }
}

class TransactionViewModelFactory(
    private val repository: TransactionRepository,
    private val accountId: Long
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        return TransactionViewModel(
            repository = repository,
            accountId = accountId
        ) as T
    }
}
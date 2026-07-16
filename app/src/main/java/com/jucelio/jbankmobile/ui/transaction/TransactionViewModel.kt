package com.jucelio.jbankmobile.ui.transaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.model.Transaction
import com.jucelio.jbankmobile.domain.usecase.transaction.GetStatementUseCase
enum class TransactionFilter {
    ALL,
    INCOME,
    EXPENSE
}

data class TransactionUiState(
    val isLoading: Boolean = true,
    val transactions: List<Transaction> = emptyList(),
    val filter: TransactionFilter = TransactionFilter.ALL,
    val errorMessage: String? = null
) {
    val filteredTransactions: List<Transaction>
        get() = when (filter) {
            TransactionFilter.ALL -> transactions

            TransactionFilter.INCOME ->
                transactions.filter {
                    it.type.uppercase() == "DEPOSIT" ||
                            it.type.uppercase().contains("RECEIVED")
                }

            TransactionFilter.EXPENSE ->
                transactions.filter {
                    it.type.uppercase() != "DEPOSIT" &&
                            !it.type.uppercase().contains("RECEIVED")
                }
        }
}
@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val getStatementUseCase: GetStatementUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val accountId: Long =
        savedStateHandle["accountId"] ?: 1L

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

            when (
                val result = getStatementUseCase(accountId)
            ) {
                is AppResult.Success -> {
                    state = state.copy(
                        isLoading = false,
                        transactions = result.data,
                        errorMessage = null
                    )
                }

                is AppResult.Failure -> {
                    state = state.copy(
                        isLoading = false,
                        transactions = emptyList(),
                        errorMessage = result.message
                    )
                }
            }
        }
    }

    fun changeFilter(filter: TransactionFilter) {
        state = state.copy(filter = filter)
    }
}


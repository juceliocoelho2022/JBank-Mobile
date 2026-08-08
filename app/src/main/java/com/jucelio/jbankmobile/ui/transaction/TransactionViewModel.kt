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
import com.jucelio.jbankmobile.domain.usecase.account.GetAccountsUseCase
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
    private val getAccountsUseCase: GetAccountsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    /**
     * Quando a tela é aberta a partir de uma conta específica
     * (ex.: AccountScreen), o id vem por argumento de navegação.
     * Quando aberta de forma genérica (ex.: aba inferior), não há
     * argumento e a conta é resolvida em tempo de carga a partir
     * das contas do usuário — evitar um valor fixo, que sempre
     * mostraria a mesma conta independente de quem estiver logado.
     */
    private val requestedAccountId: Long? =
        savedStateHandle.get<Long>("accountId")
            ?.takeIf { it > 0L }

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

            val accountId = requestedAccountId
                ?: resolveDefaultAccountId()

            if (accountId == null) {
                state = state.copy(
                    isLoading = false,
                    transactions = emptyList(),
                    errorMessage = "Nenhuma conta encontrada."
                )
                return@launch
            }

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

    private suspend fun resolveDefaultAccountId(): Long? {
        return when (
            val result = getAccountsUseCase()
        ) {
            is AppResult.Success -> result.data.firstOrNull()?.id
            is AppResult.Failure -> null
        }
    }

    fun changeFilter(filter: TransactionFilter) {
        state = state.copy(filter = filter)
    }
}


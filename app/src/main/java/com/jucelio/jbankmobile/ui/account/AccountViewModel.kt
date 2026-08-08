package com.jucelio.jbankmobile.ui.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.jucelio.jbankmobile.domain.model.Account
import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.usecase.account.GetAccountsUseCase
data class AccountUiState(
    val isLoading: Boolean = true,
    val accounts: List<Account> = emptyList(),
    val errorMessage: String? = null
)

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase
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

            when (
                val result = getAccountsUseCase()
            ) {
                is AppResult.Success -> {
                    state = AccountUiState(
                        isLoading = false,
                        accounts = result.data,
                        errorMessage = null
                    )
                }

                is AppResult.Failure -> {
                    state = AccountUiState(
                        isLoading = false,
                        accounts = emptyList(),
                        errorMessage = result.message
                    )
                }
            }
        }
    }
}

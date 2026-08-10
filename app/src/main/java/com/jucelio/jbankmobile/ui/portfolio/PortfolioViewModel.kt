package com.jucelio.jbankmobile.ui.portfolio

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.model.Investment
import com.jucelio.jbankmobile.domain.usecase.portfolio.GetInvestmentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PortfolioUiState(
    val isLoading: Boolean = true,
    val investments: List<Investment> = emptyList(),
    val errorMessage: String? = null
)

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val getInvestmentsUseCase: GetInvestmentsUseCase
) : ViewModel() {

    var state by mutableStateOf(PortfolioUiState())
        private set

    init {
        loadInvestments()
    }

    fun loadInvestments() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                errorMessage = null
            )

            when (
                val result = getInvestmentsUseCase()
            ) {
                is AppResult.Success -> {
                    state = PortfolioUiState(
                        isLoading = false,
                        investments = result.data,
                        errorMessage = null
                    )
                }

                is AppResult.Failure -> {
                    state = PortfolioUiState(
                        isLoading = false,
                        investments = emptyList(),
                        errorMessage = result.message
                    )
                }
            }
        }
    }
}

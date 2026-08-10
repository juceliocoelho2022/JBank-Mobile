package com.jucelio.jbankmobile.ui.portfolio

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.model.Portfolio
import com.jucelio.jbankmobile.domain.usecase.portfolio.GetPortfolioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PortfolioUiState(
    val isLoading: Boolean = true,
    val data: Portfolio? = null,
    val errorMessage: String? = null
)

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val getPortfolioUseCase: GetPortfolioUseCase
) : ViewModel() {

    var state by mutableStateOf(
        PortfolioUiState()
    )
        private set

    init {
        loadPortfolio()
    }

    fun loadPortfolio() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                errorMessage = null
            )

            when (
                val result = getPortfolioUseCase()
            ) {
                is AppResult.Success -> {
                    state = PortfolioUiState(
                        isLoading = false,
                        data = result.data,
                        errorMessage = null
                    )
                }

                is AppResult.Failure -> {
                    state = PortfolioUiState(
                        isLoading = false,
                        data = state.data,
                        errorMessage = result.message
                    )
                }
            }
        }
    }
}

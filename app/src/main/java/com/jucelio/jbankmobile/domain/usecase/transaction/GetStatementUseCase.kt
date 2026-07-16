package com.jucelio.jbankmobile.domain.usecase.transaction

import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.model.Transaction
import com.jucelio.jbankmobile.domain.repository.TransactionRepository
import javax.inject.Inject

class GetStatementUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(
        accountId: Long
    ): AppResult<List<Transaction>> {
        if (accountId <= 0L) {
            return AppResult.Failure(
                message = "Conta inválida."
            )
        }

        return repository.getStatement(accountId)
    }
}
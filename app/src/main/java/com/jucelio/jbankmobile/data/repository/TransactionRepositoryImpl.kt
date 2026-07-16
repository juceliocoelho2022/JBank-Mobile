package com.jucelio.jbankmobile.data.repository

import com.jucelio.jbankmobile.core.network.ApiResult
import com.jucelio.jbankmobile.core.network.safeApiCall
import com.jucelio.jbankmobile.data.mapper.toDomain
import com.jucelio.jbankmobile.data.remote.datasource.TransactionRemoteDataSource
import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.model.Transaction
import com.jucelio.jbankmobile.domain.repository.TransactionRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepositoryImpl @Inject constructor(
    private val remoteDataSource: TransactionRemoteDataSource
) : TransactionRepository {

    override suspend fun getStatement(
        accountId: Long
    ): AppResult<List<Transaction>> {
        return when (
            val result = safeApiCall {
                remoteDataSource.getStatement(accountId)
            }
        ) {
            is ApiResult.Success -> {
                AppResult.Success(
                    data = result.data.map { it.toDomain() }
                )
            }

            is ApiResult.Error -> {
                AppResult.Failure(
                    message = result.message,
                    code = result.code
                )
            }
        }
    }
}
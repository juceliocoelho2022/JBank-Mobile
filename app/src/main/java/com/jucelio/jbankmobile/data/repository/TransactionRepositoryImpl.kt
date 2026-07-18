package com.jucelio.jbankmobile.data.repository

import com.jucelio.jbankmobile.core.network.ApiResult
import com.jucelio.jbankmobile.core.network.safeApiCall
import com.jucelio.jbankmobile.data.local.datasource.TransactionLocalDataSource
import com.jucelio.jbankmobile.data.local.mapper.TransactionLocalMapper
import com.jucelio.jbankmobile.data.mapper.toEntity
import com.jucelio.jbankmobile.data.remote.datasource.TransactionRemoteDataSource
import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.model.Transaction
import com.jucelio.jbankmobile.domain.repository.TransactionRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepositoryImpl @Inject constructor(
    private val remoteDataSource: TransactionRemoteDataSource,
    private val localDataSource: TransactionLocalDataSource
) : TransactionRepository {

    override suspend fun getStatement(
        accountId: Long
    ): AppResult<List<Transaction>> {

        val remoteResult = safeApiCall {
            remoteDataSource.getStatement(accountId)
        }

        return when (remoteResult) {

            is ApiResult.Success -> {

                val entities = remoteResult.data.map { dto ->
                    dto.toEntity()
                }

                localDataSource.replaceByAccount(
                    accountId = accountId,
                    transactions = entities
                )

                val cachedTransactions =
                    localDataSource.getByAccount(accountId)
                        .map {
                            TransactionLocalMapper.toDomain(it)
                        }

                AppResult.Success(
                    data = cachedTransactions
                )
            }

            is ApiResult.Error -> {

                val cachedTransactions =
                    localDataSource.getByAccount(accountId)
                        .map {
                            TransactionLocalMapper.toDomain(it)
                        }

                if (cachedTransactions.isNotEmpty()) {

                    AppResult.Success(
                        data = cachedTransactions
                    )

                } else {

                    AppResult.Failure(
                        message = remoteResult.message,
                        code = remoteResult.code
                    )
                }
            }
        }
    }
}
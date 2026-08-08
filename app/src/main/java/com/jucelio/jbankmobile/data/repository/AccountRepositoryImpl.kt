package com.jucelio.jbankmobile.data.repository

import com.jucelio.jbankmobile.core.network.ApiResult
import com.jucelio.jbankmobile.core.network.safeApiCall
import com.jucelio.jbankmobile.data.local.datasource.AccountLocalDataSource
import com.jucelio.jbankmobile.data.local.mapper.AccountLocalMapper
import com.jucelio.jbankmobile.data.mapper.toEntity
import com.jucelio.jbankmobile.data.remote.datasource.AccountRemoteDataSource
import com.jucelio.jbankmobile.domain.model.Account
import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.repository.AccountRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRepositoryImpl @Inject constructor(
    private val remoteDataSource: AccountRemoteDataSource,
    private val localDataSource: AccountLocalDataSource
) : AccountRepository {

    override suspend fun getAccounts(): AppResult<List<Account>> {

        val remoteResult = safeApiCall {
            remoteDataSource.getAccounts()
        }

        return when (remoteResult) {

            is ApiResult.Success -> {
                val entities = remoteResult.data.map { dto ->
                    dto.toEntity()
                }

                localDataSource.saveAll(entities)

                val cachedAccounts = localDataSource
                    .getAccounts()
                    .map { entity ->
                        AccountLocalMapper.toDomain(entity)
                    }

                AppResult.Success(
                    data = cachedAccounts
                )
            }

            is ApiResult.Error -> {
                val cachedAccounts = localDataSource
                    .getAccounts()
                    .map { entity ->
                        AccountLocalMapper.toDomain(entity)
                    }

                if (cachedAccounts.isNotEmpty()) {
                    AppResult.Success(
                        data = cachedAccounts
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
package com.jucelio.jbankmobile.data.repository

import com.jucelio.jbankmobile.core.network.ApiResult
import com.jucelio.jbankmobile.core.network.safeApiCall
import com.jucelio.jbankmobile.data.mapper.toDomain
import com.jucelio.jbankmobile.data.remote.datasource.AccountRemoteDataSource
import com.jucelio.jbankmobile.domain.model.Account
import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.repository.AccountRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRepositoryImpl @Inject constructor(
    private val remoteDataSource: AccountRemoteDataSource
) : AccountRepository {

    override suspend fun getAccounts(): AppResult<List<Account>> {
        return when (
            val result = safeApiCall {
                remoteDataSource.getAccounts()
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
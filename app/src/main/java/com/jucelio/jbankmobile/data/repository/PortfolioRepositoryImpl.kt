package com.jucelio.jbankmobile.data.repository

import com.jucelio.jbankmobile.core.network.ApiResult
import com.jucelio.jbankmobile.core.network.safeApiCall
import com.jucelio.jbankmobile.data.local.datasource.InvestmentLocalDataSource
import com.jucelio.jbankmobile.data.local.mapper.InvestmentLocalMapper
import com.jucelio.jbankmobile.data.mapper.toEntity
import com.jucelio.jbankmobile.data.remote.datasource.PortfolioRemoteDataSource
import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.model.Investment
import com.jucelio.jbankmobile.domain.repository.PortfolioRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PortfolioRepositoryImpl @Inject constructor(
    private val remoteDataSource: PortfolioRemoteDataSource,
    private val localDataSource: InvestmentLocalDataSource
) : PortfolioRepository {

    override suspend fun getInvestments(): AppResult<List<Investment>> {

        val remoteResult = safeApiCall {
            remoteDataSource.getInvestments()
        }

        return when (remoteResult) {

            is ApiResult.Success -> {
                val entities = remoteResult.data.map { dto ->
                    dto.toEntity()
                }

                localDataSource.saveAll(entities)

                val cachedInvestments = localDataSource
                    .getInvestments()
                    .map { entity ->
                        InvestmentLocalMapper.toDomain(entity)
                    }

                AppResult.Success(
                    data = cachedInvestments
                )
            }

            is ApiResult.Error -> {
                val cachedInvestments = localDataSource
                    .getInvestments()
                    .map { entity ->
                        InvestmentLocalMapper.toDomain(entity)
                    }

                if (cachedInvestments.isNotEmpty()) {
                    AppResult.Success(
                        data = cachedInvestments
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

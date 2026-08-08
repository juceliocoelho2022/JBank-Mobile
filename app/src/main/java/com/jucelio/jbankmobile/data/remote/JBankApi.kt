package com.jucelio.jbankmobile.data.remote

import com.jucelio.jbankmobile.data.remote.dto.AccountResponseDto
import com.jucelio.jbankmobile.data.remote.dto.DashboardResponseDto
import com.jucelio.jbankmobile.data.remote.dto.LoginRequest
import com.jucelio.jbankmobile.data.remote.dto.LoginResponse
import com.jucelio.jbankmobile.data.remote.dto.NotificationResponseDto
import com.jucelio.jbankmobile.data.remote.dto.TransactionResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface JBankApi {

    @POST("api/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @GET("api/dashboard")
    suspend fun getDashboard(): DashboardResponseDto

    @GET("api/accounts")
    suspend fun getAccounts(): List<AccountResponseDto>

    @GET("api/transactions/statement/{accountId}")
    suspend fun getStatement(
        @Path("accountId") accountId: Long
    ): List<TransactionResponseDto>

    @GET("api/notifications")
    suspend fun getNotifications(): List<NotificationResponseDto>
}
package com.jucelio.jbankmobile

import android.content.Context
import com.jucelio.jbankmobile.data.local.TokenStore
import com.jucelio.jbankmobile.data.remote.ApiFactory
import com.jucelio.jbankmobile.data.repository.AccountRepository
import com.jucelio.jbankmobile.data.repository.AuthRepository
import com.jucelio.jbankmobile.data.repository.DashboardRepository
import com.jucelio.jbankmobile.data.repository.NotificationRepository
import com.jucelio.jbankmobile.data.repository.TransactionRepository

class AppContainer(
    context: Context
) {

    val tokenStore = TokenStore(context)

    private val api = ApiFactory.create(tokenStore)

    val authRepository = AuthRepository(
        api = api,
        tokenStore = tokenStore
    )

    val dashboardRepository = DashboardRepository(
        api = api
    )

    val accountRepository = AccountRepository(
        api = api
    )
    val transactionRepository = TransactionRepository(
        api = api
    )
    val notificationRepository = NotificationRepository(
        api = api
    )
}
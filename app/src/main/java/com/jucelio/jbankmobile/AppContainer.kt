package com.jucelio.jbankmobile

import android.content.Context
import com.jucelio.jbankmobile.core.session.SessionManager
import com.jucelio.jbankmobile.data.local.session.DataStoreSessionStorage
import com.jucelio.jbankmobile.data.local.session.SessionStorage
import com.jucelio.jbankmobile.data.remote.ApiFactory
import com.jucelio.jbankmobile.data.repository.AccountRepository
import com.jucelio.jbankmobile.data.repository.AuthRepository
import com.jucelio.jbankmobile.data.repository.DashboardRepository
import com.jucelio.jbankmobile.data.repository.NotificationRepository
import com.jucelio.jbankmobile.data.repository.TransactionRepository

class AppContainer(
    context: Context
) {

    private val applicationContext =
        context.applicationContext

    val sessionStorage: SessionStorage =
        DataStoreSessionStorage(
            context = applicationContext
        )

    val sessionManager =
        SessionManager(
            sessionStorage = sessionStorage
        )

    private val api =
        ApiFactory.create(
            sessionStorage = sessionStorage
        )

    val authRepository =
        AuthRepository(
            api = api,
            sessionManager = sessionManager
        )

    val dashboardRepository =
        DashboardRepository(
            api = api
        )

    val accountRepository =
        AccountRepository(
            api = api
        )

    val transactionRepository =
        TransactionRepository(
            api = api
        )

    val notificationRepository =
        NotificationRepository(
            api = api
        )
}
package com.jucelio.jbankmobile.core.network

import com.jucelio.jbankmobile.data.local.session.SessionStorage
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Interceptor responsável por adicionar automaticamente
 * o token JWT às requisições autenticadas.
 *
 * O token nunca deve ser exibido em logs.
 */
@Singleton
class AuthInterceptor @Inject constructor(
    private val sessionStorage: SessionStorage
) : Interceptor {

    override fun intercept(
        chain: Interceptor.Chain
    ): Response {
        val originalRequest = chain.request()

        val accessToken = runBlocking {
            sessionStorage
                .getSession()
                ?.accessToken
        }

        val authenticatedRequest = originalRequest
            .newBuilder()
            .apply {
                header(
                    name = "Accept",
                    value = "application/json"
                )

                if (
                    !accessToken.isNullOrBlank() &&
                    originalRequest.header("Authorization") == null
                ) {
                    header(
                        name = "Authorization",
                        value = "Bearer $accessToken"
                    )
                }
            }
            .build()

        return chain.proceed(authenticatedRequest)
    }
}
package com.jucelio.jbankmobile.data.remote

import com.jucelio.jbankmobile.core.network.AuthInterceptor
import com.jucelio.jbankmobile.data.local.session.SessionStorage
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiFactory {

    private const val API_BASE_URL =
        "http://10.0.2.2:8081/"

    fun create(
        sessionStorage: SessionStorage
    ): JBankApi {
        val loggingInterceptor =
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC

                redactHeader("Authorization")
                redactHeader("Cookie")
                redactHeader("Set-Cookie")
            }

        val authInterceptor =
            AuthInterceptor(sessionStorage)

        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(
                30,
                TimeUnit.SECONDS
            )
            .readTimeout(
                30,
                TimeUnit.SECONDS
            )
            .writeTimeout(
                30,
                TimeUnit.SECONDS
            )
            .retryOnConnectionFailure(true)
            .build()

        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(client)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(JBankApi::class.java)
    }
}
package com.jucelio.jbankmobile.core.network.retrofit

import com.jucelio.jbankmobile.BuildConfig
import com.jucelio.jbankmobile.core.network.interceptor.AuthInterceptor
import com.jucelio.jbankmobile.core.network.interceptor.LoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    fun create(
        tokenProvider: suspend () -> String?
    ): Retrofit {

        val client = OkHttpClient.Builder()

            .addInterceptor(
                AuthInterceptor {

                    kotlinx.coroutines.runBlocking {

                        tokenProvider()

                    }

                }
            )

            .addInterceptor(
                LoggingInterceptor.create()
            )

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

            .build()

        return Retrofit.Builder()

            .baseUrl(
                BuildConfig.API_BASE_URL
            )

            .client(client)

            .addConverterFactory(
                GsonConverterFactory.create()
            )

            .build()

    }

}
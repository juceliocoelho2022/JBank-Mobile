package com.jucelio.jbankmobile.di

import android.content.Context
import com.jucelio.jbankmobile.data.local.session.DataStoreSessionStorage
import com.jucelio.jbankmobile.data.local.session.SessionStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo responsável pelas dependências relacionadas
 * à sessão autenticada do usuário.
 */
@Module
@InstallIn(SingletonComponent::class)
object SessionModule {

    @Provides
    @Singleton
    fun provideSessionStorage(
        @ApplicationContext context: Context
    ): SessionStorage {
        return DataStoreSessionStorage(
            context = context
        )
    }
}
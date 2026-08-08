package com.jucelio.jbankmobile.di

import com.jucelio.jbankmobile.data.local.session.DataStoreSessionStorage
import com.jucelio.jbankmobile.data.local.session.SessionStorage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Define qual implementação deve ser usada quando
 * uma classe solicitar a interface SessionStorage.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class SessionModule {

    @Binds
    @Singleton
    abstract fun bindSessionStorage(
        implementation: DataStoreSessionStorage
    ): SessionStorage
}
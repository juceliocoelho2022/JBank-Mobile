package com.jucelio.jbankmobile.data.remote.datasource

import com.jucelio.jbankmobile.data.remote.JBankApi
import com.jucelio.jbankmobile.fixtures.notification.NotificationFixtures
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NotificationRemoteDataSourceTest {

    private lateinit var api: JBankApi
    private lateinit var dataSource: NotificationRemoteDataSource

    @Before
    fun setup() {
        api = mockk()
        dataSource = NotificationRemoteDataSource(api)
    }

    @Test
    fun `getNotifications deve retornar lista da API`() = runTest {

        val expected =
            NotificationFixtures.notificationResponseDtoList()

        coEvery {
            api.getNotifications()
        } returns expected

        val result = dataSource.getNotifications()

        assertEquals(expected, result)

        coVerify(exactly = 1) {
            api.getNotifications()
        }
    }

    @Test(expected = RuntimeException::class)
    fun `getNotifications deve propagar excecao`() = runTest {

        coEvery {
            api.getNotifications()
        } throws RuntimeException("Erro na API")

        dataSource.getNotifications()
    }
}
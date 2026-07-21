package com.jucelio.jbankmobile.data.local.datasource

import com.jucelio.jbankmobile.data.local.dao.NotificationDao
import com.jucelio.jbankmobile.data.local.mapper.NotificationLocalMapper
import com.jucelio.jbankmobile.fixtures.notification.NotificationFixtures
import io.mockk.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NotificationLocalDataSourceTest {

    private lateinit var dao: NotificationDao
    private lateinit var dataSource: NotificationLocalDataSource

    @Before
    fun setup() {
        dao = mockk()
        dataSource = NotificationLocalDataSource(dao)
    }

    @Test
    fun `observeNotifications deve retornar Flow do DAO`() = runTest {

        val notifications = NotificationFixtures.notificationList()
            .map(NotificationLocalMapper::toEntity)

        every {
            dao.observeNotifications()
        } returns flowOf(notifications)

        val result = dataSource.observeNotifications().first()

        assertEquals(notifications, result)

        verify(exactly = 1) {
            dao.observeNotifications()
        }
    }

    @Test
    fun `getNotifications deve retornar lista do DAO`() = runTest {

        val notifications = NotificationFixtures.notificationList()
            .map(NotificationLocalMapper::toEntity)

        coEvery {
            dao.getNotifications()
        } returns notifications

        val result = dataSource.getNotifications()

        assertEquals(notifications, result)

        coVerify(exactly = 1) {
            dao.getNotifications()
        }
    }

    @Test
    fun `save deve inserir notificacao`() = runTest {

        val notification =
            NotificationLocalMapper.toEntity(
                NotificationFixtures.notification()
            )

        coEvery {
            dao.insertNotification(notification)
        } returns Unit

        dataSource.save(notification)

        coVerify(exactly = 1) {
            dao.insertNotification(notification)
        }
    }

    @Test
    fun `saveAll deve substituir notificacoes`() = runTest {

        val notifications = NotificationFixtures.notificationList()
            .map(NotificationLocalMapper::toEntity)

        coEvery {
            dao.replaceNotifications(notifications)
        } returns Unit

        dataSource.saveAll(notifications)

        coVerify(exactly = 1) {
            dao.replaceNotifications(notifications)
        }
    }

    @Test
    fun `clear deve limpar notificacoes`() = runTest {

        coEvery {
            dao.clearNotifications()
        } returns Unit

        dataSource.clear()

        coVerify(exactly = 1) {
            dao.clearNotifications()
        }
    }
}
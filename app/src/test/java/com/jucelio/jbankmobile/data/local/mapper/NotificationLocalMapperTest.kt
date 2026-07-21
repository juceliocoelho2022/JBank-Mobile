package com.jucelio.jbankmobile.data.local.mapper

import com.jucelio.jbankmobile.fixtures.notification.NotificationFixtures
import org.junit.Assert
import org.junit.Test

class NotificationLocalMapperTest {

    @Test
    fun `toEntity deve converter Notification para NotificationEntity`() {

        val notification = NotificationFixtures.notification()

        val entity = NotificationLocalMapper.toEntity(notification)

        Assert.assertEquals(notification.id, entity.id)
        Assert.assertEquals(notification.title, entity.title)
        Assert.assertEquals(notification.message, entity.message)
        Assert.assertEquals(notification.type, entity.type)
        Assert.assertEquals(notification.isRead, entity.isRead)
        Assert.assertEquals(notification.createdAt, entity.createdAt)
    }

    @Test
    fun `toDomain deve converter NotificationEntity para Notification`() {

        val notification = NotificationFixtures.notification()

        val entity = NotificationLocalMapper.toEntity(notification)

        val result = NotificationLocalMapper.toDomain(entity)

        Assert.assertEquals(notification, result)
    }

    @Test
    fun `conversao ida e volta deve preservar os dados`() {

        val original = NotificationFixtures.notification()

        val entity = NotificationLocalMapper.toEntity(original)

        val result = NotificationLocalMapper.toDomain(entity)

        Assert.assertEquals(original, result)
    }

    @Test
    fun `deve preservar notificacao lida`() {

        val notification = NotificationFixtures.notification().copy(
            isRead = true
        )

        val result = NotificationLocalMapper.toDomain(
            NotificationLocalMapper.toEntity(notification)
        )

        Assert.assertTrue(result.isRead)
    }

    @Test
    fun `deve preservar notificacao nao lida`() {

        val notification = NotificationFixtures.notification().copy(
            isRead = false
        )

        val result = NotificationLocalMapper.toDomain(
            NotificationLocalMapper.toEntity(notification)
        )

        Assert.assertFalse(result.isRead)
    }
}
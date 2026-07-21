package com.jucelio.jbankmobile.data.mapper

import com.jucelio.jbankmobile.fixtures.notification.NotificationFixtures
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class NotificationMapperTest {

    @Test
    fun `toDomain deve converter NotificationResponseDto para Notification corretamente`() {

        // Arrange
        val dto = NotificationFixtures.notificationResponseDto()

        // Act
        val result = dto.toDomain()

        // Assert
        assertEquals(dto.id, result.id)
        assertEquals(dto.title, result.title)
        assertEquals(dto.message, result.message)
        assertEquals(dto.type, result.type)
        assertEquals(dto.read, result.isRead)
        assertEquals(dto.createdAt, result.createdAt)
    }

    @Test
    fun `toDomain deve converter createdAt nulo para string vazia`() {

        // Arrange
        val dto = NotificationFixtures.notificationResponseDto().copy(
            createdAt = null
        )

        // Act
        val result = dto.toDomain()

        // Assert
        assertEquals("", result.createdAt)
    }

    @Test
    fun `toDomain deve manter notificacao lida`() {

        // Arrange
        val dto = NotificationFixtures.notificationResponseDto().copy(
            read = true
        )

        // Act
        val result = dto.toDomain()

        // Assert
        assertTrue(result.isRead)
    }

    @Test
    fun `toDomain deve manter notificacao nao lida`() {

        // Arrange
        val dto = NotificationFixtures.notificationResponseDto().copy(
            read = false
        )

        // Act
        val result = dto.toDomain()

        // Assert
        assertFalse(result.isRead)
    }
}
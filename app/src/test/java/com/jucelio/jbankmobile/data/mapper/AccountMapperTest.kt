package com.jucelio.jbankmobile.data.mapper

import com.jucelio.jbankmobile.fixtures.account.AccountFixtures
import org.junit.Assert.assertEquals
import org.junit.Test

class AccountMapperTest {

    @Test
    fun `toDomain deve converter AccountResponseDto para Account corretamente`() {
        // Arrange
        val dto = AccountFixtures.accountResponseDto()

        // Act
        val result = dto.toDomain()

        // Assert
        assertEquals(dto.id, result.id)
        assertEquals(dto.number, result.number)
        assertEquals(dto.type, result.type)
        assertEquals(dto.balance, result.balance)
        assertEquals(dto.active, result.active)
        assertEquals(dto.customerId, result.customerId)
    }

    @Test
    fun `toEntity deve converter AccountResponseDto para AccountEntity corretamente`() {
        // Arrange
        val dto = AccountFixtures.accountResponseDto()

        // Act
        val result = dto.toEntity()

        // Assert
        assertEquals(dto.id, result.id)
        assertEquals(dto.number, result.number)
        assertEquals(dto.type, result.type)
        assertEquals(dto.balance, result.balance)
        assertEquals(dto.active, result.active)
        assertEquals(dto.customerId, result.customerId)
    }

    @Test
    fun `toDomain deve manter customerId nulo`() {
        // Arrange
        val dto = AccountFixtures.accountResponseDto().copy(
            customerId = null
        )

        // Act
        val result = dto.toDomain()

        // Assert
        assertEquals(null, result.customerId)
    }

    @Test
    fun `toEntity deve manter customerId nulo`() {
        // Arrange
        val dto = AccountFixtures.accountResponseDto().copy(
            customerId = null
        )

        // Act
        val result = dto.toEntity()

        // Assert
        assertEquals(null, result.customerId)
    }
}
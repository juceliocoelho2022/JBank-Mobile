package com.jucelio.jbankmobile.data.mapper

import com.jucelio.jbankmobile.fixtures.transaction.TransactionFixtures
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class TransactionMapperTest {

    @Test
    fun `toDomain deve converter TransactionResponseDto corretamente`() {

        val dto = TransactionFixtures.transactionResponseDto()

        val result = dto.toDomain()

        assertEquals(dto.id, result.id)
        assertEquals(dto.type, result.type)
        assertEquals(dto.amount, result.amount)
        assertEquals(dto.description, result.description)
        assertEquals(dto.sourceAccountId, result.sourceAccountId)
        assertEquals(dto.targetAccountId, result.targetAccountId)
        assertEquals(dto.createdAt, result.createdAt)
    }

    @Test
    fun `toEntity deve converter TransactionResponseDto corretamente`() {

        val dto = TransactionFixtures.transactionResponseDto()

        val result = dto.toEntity()

        assertEquals(dto.id, result.id)
        assertEquals(dto.type, result.type)
        assertEquals(dto.amount, result.amount)
        assertEquals(dto.description, result.description)
        assertEquals(dto.sourceAccountId, result.sourceAccountId)
        assertEquals(dto.targetAccountId, result.targetAccountId)
        assertEquals(dto.createdAt, result.createdAt)
    }

    @Test
    fun `toDomain deve preencher valores nulos com defaults`() {

        val dto = TransactionFixtures.transactionResponseDto().copy(
            id = null,
            type = null,
            amount = null,
            description = null,
            createdAt = null
        )

        val result = dto.toDomain()

        assertEquals(0L, result.id)
        assertEquals("", result.type)
        assertEquals(BigDecimal.ZERO, result.amount)
        assertEquals("", result.description)
        assertEquals("", result.createdAt)
    }

    @Test
    fun `toEntity deve preencher valores nulos com defaults`() {

        val dto = TransactionFixtures.transactionResponseDto().copy(
            id = null,
            type = null,
            amount = null,
            description = null,
            createdAt = null
        )

        val result = dto.toEntity()

        assertEquals(0L, result.id)
        assertEquals("", result.type)
        assertEquals(BigDecimal.ZERO, result.amount)
        assertEquals("", result.description)
        assertEquals("", result.createdAt)
    }
}
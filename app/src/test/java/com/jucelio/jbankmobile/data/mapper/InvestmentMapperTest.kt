package com.jucelio.jbankmobile.data.mapper

import com.jucelio.jbankmobile.fixtures.portfolio.InvestmentFixtures
import org.junit.Assert.assertEquals
import org.junit.Test

class InvestmentMapperTest {

    @Test
    fun `toDomain deve converter InvestmentResponseDto para Investment corretamente`() {
        // Arrange
        val dto = InvestmentFixtures.investmentResponseDto()

        // Act
        val result = dto.toDomain()

        // Assert
        assertEquals(dto.id, result.id)
        assertEquals(dto.name, result.name)
        assertEquals(dto.type, result.type)
        assertEquals(dto.investedAmount, result.investedAmount)
        assertEquals(dto.currentValue, result.currentValue)
        assertEquals(dto.profitability, result.profitability)
    }

    @Test
    fun `toEntity deve converter InvestmentResponseDto para InvestmentEntity corretamente`() {
        // Arrange
        val dto = InvestmentFixtures.investmentResponseDto()

        // Act
        val result = dto.toEntity()

        // Assert
        assertEquals(dto.id, result.id)
        assertEquals(dto.name, result.name)
        assertEquals(dto.type, result.type)
        assertEquals(dto.investedAmount, result.investedAmount)
        assertEquals(dto.currentValue, result.currentValue)
        assertEquals(dto.profitability, result.profitability)
    }
}

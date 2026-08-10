package com.jucelio.jbankmobile.data.local.mapper

import com.jucelio.jbankmobile.fixtures.portfolio.InvestmentFixtures
import org.junit.Assert
import org.junit.Test
import java.math.BigDecimal

class InvestmentLocalMapperTest {

    @Test
    fun `toEntity deve converter Investment para InvestmentEntity corretamente`() {
        // Arrange
        val investment = InvestmentFixtures.investment()

        // Act
        val result = InvestmentLocalMapper.toEntity(investment)

        // Assert
        Assert.assertEquals(investment.id, result.id)
        Assert.assertEquals(investment.name, result.name)
        Assert.assertEquals(investment.type, result.type)
        Assert.assertEquals(investment.investedAmount, result.investedAmount)
        Assert.assertEquals(investment.currentValue, result.currentValue)
        Assert.assertEquals(investment.profitability, result.profitability)
    }

    @Test
    fun `toDomain deve converter InvestmentEntity para Investment corretamente`() {
        // Arrange
        val investment = InvestmentFixtures.investment()
        val entity = InvestmentLocalMapper.toEntity(investment)

        // Act
        val result = InvestmentLocalMapper.toDomain(entity)

        // Assert
        Assert.assertEquals(investment.id, result.id)
        Assert.assertEquals(investment.name, result.name)
        Assert.assertEquals(investment.type, result.type)
        Assert.assertEquals(investment.investedAmount, result.investedAmount)
        Assert.assertEquals(investment.currentValue, result.currentValue)
        Assert.assertEquals(investment.profitability, result.profitability)
    }

    @Test
    fun `conversao de ida e volta deve preservar todos os dados`() {
        // Arrange
        val original = InvestmentFixtures.investment()

        // Act
        val entity = InvestmentLocalMapper.toEntity(original)
        val result = InvestmentLocalMapper.toDomain(entity)

        // Assert
        Assert.assertEquals(original, result)
    }

    @Test
    fun `mapeamento deve preservar rentabilidade negativa`() {
        // Arrange
        val investment = InvestmentFixtures.stockInvestment()

        // Act
        val entity = InvestmentLocalMapper.toEntity(investment)
        val result = InvestmentLocalMapper.toDomain(entity)

        // Assert
        Assert.assertEquals(BigDecimal("-14.00"), entity.profitability)
        Assert.assertEquals(BigDecimal("-14.00"), result.profitability)
    }
}

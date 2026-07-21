package com.jucelio.jbankmobile.data.local.mapper

import com.jucelio.jbankmobile.fixtures.account.AccountFixtures
import org.junit.Assert
import org.junit.Test
import java.math.BigDecimal

class AccountLocalMapperTest {

    @Test
    fun `toEntity deve converter Account para AccountEntity corretamente`() {
        // Arrange
        val account = AccountFixtures.account()

        // Act
        val result = AccountLocalMapper.toEntity(account)

        // Assert
        Assert.assertEquals(account.id, result.id)
        Assert.assertEquals(account.number, result.number)
        Assert.assertEquals(account.type, result.type)
        Assert.assertEquals(account.balance, result.balance)
        Assert.assertEquals(account.active, result.active)
        Assert.assertEquals(account.customerId, result.customerId)
    }

    @Test
    fun `toDomain deve converter AccountEntity para Account corretamente`() {
        // Arrange
        val account = AccountFixtures.account()
        val entity = AccountLocalMapper.toEntity(account)

        // Act
        val result = AccountLocalMapper.toDomain(entity)

        // Assert
        Assert.assertEquals(account.id, result.id)
        Assert.assertEquals(account.number, result.number)
        Assert.assertEquals(account.type, result.type)
        Assert.assertEquals(account.balance, result.balance)
        Assert.assertEquals(account.active, result.active)
        Assert.assertEquals(account.customerId, result.customerId)
    }

    @Test
    fun `conversao de ida e volta deve preservar todos os dados`() {
        // Arrange
        val original = AccountFixtures.account()

        // Act
        val entity = AccountLocalMapper.toEntity(original)
        val result = AccountLocalMapper.toDomain(entity)

        // Assert
        Assert.assertEquals(original, result)
    }

    @Test
    fun `mapeamento deve preservar customerId nulo`() {
        // Arrange
        val account = AccountFixtures.accountWithoutCustomer()

        // Act
        val entity = AccountLocalMapper.toEntity(account)
        val result = AccountLocalMapper.toDomain(entity)

        // Assert
        Assert.assertEquals(null, entity.customerId)
        Assert.assertEquals(null, result.customerId)
    }

    @Test
    fun `mapeamento deve preservar saldo zero`() {
        // Arrange
        val account = AccountFixtures.accountWithBalance(
            balance = BigDecimal.ZERO
        )

        // Act
        val entity = AccountLocalMapper.toEntity(account)
        val result = AccountLocalMapper.toDomain(entity)

        // Assert
        Assert.assertEquals(BigDecimal.ZERO, entity.balance)
        Assert.assertEquals(BigDecimal.ZERO, result.balance)
    }
}
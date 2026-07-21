package com.jucelio.jbankmobile.data.local.mapper

import com.jucelio.jbankmobile.fixtures.transaction.TransactionFixtures
import org.junit.Assert
import org.junit.Test
import java.math.BigDecimal

class TransactionLocalMapperTest {

    @Test
    fun `toEntity deve converter Transaction para TransactionEntity corretamente`() {
        val transaction = TransactionFixtures.transaction()

        val result = TransactionLocalMapper.toEntity(transaction)

        Assert.assertEquals(transaction.id, result.id)
        Assert.assertEquals(transaction.type, result.type)
        Assert.assertEquals(transaction.amount, result.amount)
        Assert.assertEquals(transaction.description, result.description)
        Assert.assertEquals(transaction.sourceAccountId, result.sourceAccountId)
        Assert.assertEquals(transaction.targetAccountId, result.targetAccountId)
        Assert.assertEquals(transaction.createdAt, result.createdAt)
    }

    @Test
    fun `toDomain deve converter TransactionEntity para Transaction corretamente`() {
        val transaction = TransactionFixtures.transaction()
        val entity = TransactionLocalMapper.toEntity(transaction)

        val result = TransactionLocalMapper.toDomain(entity)

        Assert.assertEquals(transaction.id, result.id)
        Assert.assertEquals(transaction.type, result.type)
        Assert.assertEquals(transaction.amount, result.amount)
        Assert.assertEquals(transaction.description, result.description)
        Assert.assertEquals(transaction.sourceAccountId, result.sourceAccountId)
        Assert.assertEquals(transaction.targetAccountId, result.targetAccountId)
        Assert.assertEquals(transaction.createdAt, result.createdAt)
    }

    @Test
    fun `conversao de ida e volta deve preservar todos os dados`() {
        val original = TransactionFixtures.transaction()

        val result = TransactionLocalMapper.toDomain(
            TransactionLocalMapper.toEntity(original)
        )

        Assert.assertEquals(original, result)
    }

    @Test
    fun `mapeamento deve preservar conta de origem nula`() {
        val transaction = TransactionFixtures.depositTransaction()

        val entity = TransactionLocalMapper.toEntity(transaction)
        val result = TransactionLocalMapper.toDomain(entity)

        Assert.assertNull(entity.sourceAccountId)
        Assert.assertNull(result.sourceAccountId)
        Assert.assertEquals(transaction.targetAccountId, result.targetAccountId)
    }

    @Test
    fun `mapeamento deve preservar conta de destino nula`() {
        val transaction = TransactionFixtures.withdrawalTransaction()

        val entity = TransactionLocalMapper.toEntity(transaction)
        val result = TransactionLocalMapper.toDomain(entity)

        Assert.assertNull(entity.targetAccountId)
        Assert.assertNull(result.targetAccountId)
        Assert.assertEquals(transaction.sourceAccountId, result.sourceAccountId)
    }

    @Test
    fun `mapeamento deve preservar valor zero`() {
        val transaction = TransactionFixtures.transaction().copy(
            amount = BigDecimal.ZERO
        )

        val result = TransactionLocalMapper.toDomain(
            TransactionLocalMapper.toEntity(transaction)
        )

        Assert.assertEquals(BigDecimal.ZERO, result.amount)
    }
}
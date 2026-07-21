package com.jucelio.jbankmobile.data.local.datasource

import com.jucelio.jbankmobile.data.local.dao.AccountDao
import com.jucelio.jbankmobile.data.local.mapper.AccountLocalMapper
import com.jucelio.jbankmobile.fixtures.account.AccountFixtures
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class AccountLocalDataSourceTest {

    private lateinit var dao: AccountDao
    private lateinit var dataSource: AccountLocalDataSource

    @Before
    fun setup() {
        dao = mockk()
        dataSource = AccountLocalDataSource(dao)
    }

    @Test
    fun `observeAccounts deve retornar fluxo fornecido pelo dao`() = runTest {
        val accounts = AccountFixtures.accountList()
            .map(AccountLocalMapper::toEntity)

        every {
            dao.observeAccounts()
        } returns flowOf(accounts)

        val result = dataSource.observeAccounts().first()

        assertEquals(accounts, result)

        verify(exactly = 1) {
            dao.observeAccounts()
        }
    }

    @Test
    fun `getAccounts deve retornar contas fornecidas pelo dao`() = runTest {
        val accounts = AccountFixtures.accountList()
            .map(AccountLocalMapper::toEntity)

        coEvery {
            dao.getAccounts()
        } returns accounts

        val result = dataSource.getAccounts()

        assertEquals(accounts, result)

        coVerify(exactly = 1) {
            dao.getAccounts()
        }
    }

    @Test
    fun `getAccountById deve retornar conta correspondente`() = runTest {
        val accountId = 1L
        val account = AccountLocalMapper.toEntity(
            AccountFixtures.accountWithId(accountId)
        )

        coEvery {
            dao.getAccountById(accountId)
        } returns account

        val result = dataSource.getAccountById(accountId)

        assertEquals(account, result)

        coVerify(exactly = 1) {
            dao.getAccountById(accountId)
        }
    }

    @Test
    fun `getAccountById deve retornar nulo quando conta nao existir`() = runTest {
        val accountId = 999L

        coEvery {
            dao.getAccountById(accountId)
        } returns null

        val result = dataSource.getAccountById(accountId)

        assertNull(result)

        coVerify(exactly = 1) {
            dao.getAccountById(accountId)
        }
    }

    @Test
    fun `save deve inserir conta no dao`() = runTest {
        val account = AccountLocalMapper.toEntity(
            AccountFixtures.account()
        )

        coEvery {
            dao.insertAccount(account)
        } returns Unit

        dataSource.save(account)

        coVerify(exactly = 1) {
            dao.insertAccount(account)
        }
    }

    @Test
    fun `saveAll deve substituir contas no dao`() = runTest {
        val accounts = AccountFixtures.accountList()
            .map(AccountLocalMapper::toEntity)

        coEvery {
            dao.replaceAccounts(accounts)
        } returns Unit

        dataSource.saveAll(accounts)

        coVerify(exactly = 1) {
            dao.replaceAccounts(accounts)
        }
    }

    @Test
    fun `clear deve limpar contas no dao`() = runTest {
        coEvery {
            dao.clearAccounts()
        } returns Unit

        dataSource.clear()

        coVerify(exactly = 1) {
            dao.clearAccounts()
        }
    }

    @Test(expected = RuntimeException::class)
    fun `getAccounts deve propagar excecao do dao`() = runTest {
        coEvery {
            dao.getAccounts()
        } throws RuntimeException("Erro no banco local")

        dataSource.getAccounts()
    }
}
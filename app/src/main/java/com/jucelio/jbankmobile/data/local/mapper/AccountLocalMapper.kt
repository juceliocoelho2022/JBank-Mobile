package com.jucelio.jbankmobile.data.local.mapper

import com.jucelio.jbankmobile.data.local.entity.AccountEntity
import com.jucelio.jbankmobile.domain.model.Account

object AccountLocalMapper {

    fun toEntity(account: Account): AccountEntity {
        return AccountEntity(
            id = account.id,
            number = account.number,
            type = account.type,
            balance = account.balance,
            active = account.active,
            customerId = account.customerId
        )
    }

    fun toDomain(entity: AccountEntity): Account {
        return Account(
            id = entity.id,
            number = entity.number,
            type = entity.type,
            balance = entity.balance,
            active = entity.active,
            customerId = entity.customerId
        )
    }
}
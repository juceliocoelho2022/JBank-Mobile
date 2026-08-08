package com.jucelio.jbankmobile.domain.usecase

interface BaseUseCase<in P, out R> {

    suspend operator fun invoke(
        param: P
    ): R
}
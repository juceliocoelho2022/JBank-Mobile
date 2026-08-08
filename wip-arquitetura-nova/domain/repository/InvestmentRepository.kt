package com.jucelio.jbankmobile.domain.repository

import com.jucelio.jbankmobile.domain.model.Investment

interface InvestmentRepository {

    suspend fun getInvestments(): List<Investment>
}
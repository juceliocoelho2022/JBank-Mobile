package com.jucelio.jbankmobile.core.utils

object Validators {

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS
            .matcher(email)
            .matches()
    }

    fun isPasswordValid(password: String): Boolean {
        return password.length >= 8
    }

    fun isCpfValid(cpf: String): Boolean {
        return cpf.length == 11
    }
}
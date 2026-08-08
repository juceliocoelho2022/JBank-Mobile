package com.jucelio.jbankmobile.core.network.util

import com.jucelio.jbankmobile.core.network.model.ApiResult
import retrofit2.HttpException
import java.io.IOException

suspend inline fun <T> safeApiCall(
    crossinline apiCall: suspend () -> T
): ApiResult<T> {

    return try {

        ApiResult.Success(
            apiCall()
        )

    } catch (e: HttpException) {

        ApiResult.Error(
            code = e.code(),
            message = e.message()
        )

    } catch (e: IOException) {

        ApiResult.Error(
            code = null,
            message = "Sem conexão com a internet."
        )

    } catch (e: Exception) {

        ApiResult.Error(
            code = null,
            message = e.message ?: "Erro desconhecido."
        )

    }

}
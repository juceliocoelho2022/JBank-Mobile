package com.jucelio.jbankmobile.core.network

import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * Executa uma chamada de API e converte exceções técnicas
 * em um resultado previsível para repositories e ViewModels.
 */
suspend inline fun <T> safeApiCall(
    crossinline apiCall: suspend () -> T
): ApiResult<T> {
    return try {
        ApiResult.Success(
            data = apiCall()
        )
    } catch (exception: CancellationException) {
        /*
         * Cancelamentos de coroutine precisam continuar
         * sendo propagados.
         */
        throw exception
    } catch (exception: SocketTimeoutException) {
        ApiResult.Error(
            type = NetworkError.TIMEOUT,
            message = "A solicitação demorou mais que o esperado."
        )
    } catch (exception: IOException) {
        ApiResult.Error(
            type = NetworkError.NO_INTERNET,
            message = "Não foi possível conectar. Verifique sua internet."
        )
    } catch (exception: HttpException) {
        mapHttpException(exception)
    } catch (exception: Exception) {
        ApiResult.Error(
            type = NetworkError.UNKNOWN,
            message = exception.message
                ?: "Ocorreu um erro inesperado."
        )
    }
}

fun mapHttpException(
    exception: HttpException
): ApiResult.Error {
    val code = exception.code()

    return when (code) {
        401 -> ApiResult.Error(
            type = NetworkError.UNAUTHORIZED,
            message = "Sua sessão expirou. Entre novamente.",
            code = code
        )

        403 -> ApiResult.Error(
            type = NetworkError.FORBIDDEN,
            message = "Você não possui permissão para esta operação.",
            code = code
        )

        404 -> ApiResult.Error(
            type = NetworkError.NOT_FOUND,
            message = "O recurso solicitado não foi encontrado.",
            code = code
        )

        in 400..499 -> ApiResult.Error(
            type = NetworkError.CLIENT_ERROR,
            message = "Não foi possível processar a solicitação.",
            code = code
        )

        in 500..599 -> ApiResult.Error(
            type = NetworkError.SERVER_ERROR,
            message = "O serviço está temporariamente indisponível.",
            code = code
        )

        else -> ApiResult.Error(
            type = NetworkError.UNKNOWN,
            message = "Ocorreu um erro na comunicação com o servidor.",
            code = code
        )
    }
}
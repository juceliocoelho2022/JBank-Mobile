package com.jucelio.jbankmobile.core.network.exception

sealed class NetworkException(
    message: String
) : Exception(message)

class UnauthorizedException :
    NetworkException("Usuário não autenticado.")

class ForbiddenException :
    NetworkException("Acesso negado.")

class NotFoundException :
    NetworkException("Recurso não encontrado.")

class ServerException :
    NetworkException("Erro interno do servidor.")

class ConnectionException :
    NetworkException("Sem conexão com a internet.")

class UnknownNetworkException :
    NetworkException("Erro desconhecido.")
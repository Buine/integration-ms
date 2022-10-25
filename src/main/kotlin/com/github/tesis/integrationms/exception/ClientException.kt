package com.github.tesis.integrationms.exception

data class ClientException(
    val code: String = CLIENT_EXCEPTION,
    val messages: List<String>
) : RuntimeException()

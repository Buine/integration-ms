package com.github.tesis.integrationms.domain.responses

import com.github.tesis.integrationms.exception.CLIENT_EXCEPTION

data class ErrorResponse(
    val code: String = CLIENT_EXCEPTION,
    val messages: List<String>
)

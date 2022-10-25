package com.github.tesis.integrationms.domain.responses

import com.github.tesis.integrationms.domain.enums.StatusEnum
import java.time.Instant
import java.util.UUID

data class IntegrationResponse(
    val code: UUID,
    val name: String,
    val status: StatusEnum,
    val nameDatabase: String,
    val createdAt: Instant,
    val updatedAt: Instant
)

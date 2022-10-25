package com.github.tesis.integrationms.domain.responses

import com.github.tesis.integrationms.domain.clients.requests.Config
import com.github.tesis.integrationms.domain.enums.StatusEnum
import java.time.Instant
import java.util.UUID

class IntegrationDetailResponse(
    val code: UUID,
    val name: String,
    val status: StatusEnum,
    val checksum: String,
    val createdAt: Instant,
    val updatedAt: Instant,
    val config: Config
)

package com.github.tesis.integrationms.domain.clients.requests

import java.util.UUID

data class SchemaRequest(
    val integrationCode: UUID,
    val config: Config
)

data class Config(
    val host: String,
    val username: String,
    val password: String,
    val name: String,
    val ssl: Boolean,
    val port: Int
)

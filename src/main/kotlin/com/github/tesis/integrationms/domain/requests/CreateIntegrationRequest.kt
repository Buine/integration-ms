package com.github.tesis.integrationms.domain.requests

import com.github.tesis.integrationms.domain.entities.Integration
import java.util.UUID

data class CreateIntegrationRequest(
    val name: String,
    val database: Database
) {
    data class Database(
        val host: String,
        val username: String,
        val password: String,
        val name: String,
        val ssl: Boolean,
        val port: Int
    )

    fun toIntegration(userCode: UUID): Integration =
        Integration(
            name = name,
            userCode = userCode,
            dbName = database.name,
            dbPassword = database.password,
            dbUsername = database.username,
            ssl = database.ssl,
            port = database.port,
            host = database.host
        )
}

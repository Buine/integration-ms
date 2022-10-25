package com.github.tesis.integrationms.domain.requests

data class UpdateIntegrationRequest(
    val name: String?,
    val config: Database?
) {
    data class Database(
        val host: String?,
        val username: String?,
        val password: String?,
        val name: String?,
        val ssl: Boolean?,
        val port: Int?
    )
}

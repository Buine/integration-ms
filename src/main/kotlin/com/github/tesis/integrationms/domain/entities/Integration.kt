package com.github.tesis.integrationms.domain.entities

import com.github.tesis.integrationms.domain.clients.requests.Config
import com.github.tesis.integrationms.domain.clients.requests.SchemaRequest
import com.github.tesis.integrationms.domain.enums.StatusEnum
import com.github.tesis.integrationms.domain.requests.UpdateIntegrationRequest
import com.github.tesis.integrationms.domain.responses.IntegrationDetailResponse
import com.github.tesis.integrationms.domain.responses.IntegrationResponse
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.time.Instant
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "integration")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType::class)
open class Integration(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    open var id: Long = 0,

    @Column(name = "code", nullable = false)
    open var code: UUID = UUID.randomUUID(),

    @Column(name = "user_code", nullable = false)
    open var userCode: UUID,

    @Column(name = "name", nullable = false)
    open var name: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    open var status: StatusEnum = StatusEnum.SUCCESSFULLY,

    @Column(name = "host", nullable = false)
    open var host: String,

    @Column(name = "port", nullable = false)
    open var port: Int = 5432,

    @Column(name = "db_name", nullable = false)
    open var dbName: String,

    @Column(name = "db_username", nullable = false)
    open var dbUsername: String,

    @Column(name = "db_password", nullable = false)
    open var dbPassword: String,

    @Column(name = "ssl", nullable = false)
    open var ssl: Boolean = false,

    @Column(name = "schema", nullable = false, columnDefinition = "jsonb")
    @Type(type = "jsonb")
    open var schema: Map<String, Any>? = null,

    @Column(name = "checksum")
    open var checksum: String = "",

    @Column(name = "created_at", nullable = false)
    open var createdAt: Instant = Instant.now(),

    @Column(name = "updated_at", nullable = false)
    open var updatedAt: Instant = Instant.now()
) {
    fun updateFromRequest(updateIntegrationRequest: UpdateIntegrationRequest) {
        name = updateIntegrationRequest.name ?: name
        updateIntegrationRequest.config?.let {
            host = it.host ?: host
            dbName = it.name ?: dbName
            dbPassword = it.password ?: dbPassword
            dbUsername = it.username ?: dbUsername
            port = it.port ?: port
            ssl = it.ssl ?: ssl
        }
    }

    fun toSchemaRequest(): SchemaRequest =
        SchemaRequest(
            integrationCode = code,
            config = Config(
                host = host,
                name = dbName,
                username = dbUsername,
                port = port,
                ssl = ssl,
                password = dbPassword
            )
        )

    fun toResponse(): IntegrationResponse =
        IntegrationResponse(
            code = code,
            name = name,
            status = status,
            nameDatabase = dbName,
            createdAt = createdAt,
            updatedAt = updatedAt
        )

    fun toDetailResponse(): IntegrationDetailResponse =
        IntegrationDetailResponse(
            code = code,
            name = name,
            status = status,
            checksum = checksum,
            createdAt = createdAt,
            updatedAt = updatedAt,
            config = Config(
                host = host,
                name = dbName,
                username = dbUsername,
                port = port,
                ssl = ssl,
                password = "****"
            )
        )
}

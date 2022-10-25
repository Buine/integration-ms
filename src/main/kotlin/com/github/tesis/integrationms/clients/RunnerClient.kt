package com.github.tesis.integrationms.clients

import com.github.tesis.integrationms.clients.configurations.FeignConfiguration
import com.github.tesis.integrationms.constants.ExternalRoutes
import com.github.tesis.integrationms.constants.HEADER_USER
import com.github.tesis.integrationms.domain.clients.requests.SchemaRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import java.util.UUID

@FeignClient(
    name = ExternalRoutes.Runner.NAME,
    url = "\${microservices.runner}", configuration = [FeignConfiguration::class]
)
interface RunnerClient {
    @PostMapping(
        value = [ExternalRoutes.Runner.REQUEST_SCHEMA],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun generateSchema(
        @RequestBody schemaRequest: SchemaRequest,
        @RequestHeader(HEADER_USER) userCode: UUID
    ): ResponseEntity<Map<String, Any>>
}

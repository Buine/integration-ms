package com.github.tesis.integrationms.services

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.tesis.integrationms.clients.RunnerClient
import com.github.tesis.integrationms.constants.ErrorCodes
import com.github.tesis.integrationms.domain.requests.CreateIntegrationRequest
import com.github.tesis.integrationms.domain.requests.UpdateIntegrationRequest
import com.github.tesis.integrationms.domain.responses.IntegrationDetailResponse
import com.github.tesis.integrationms.domain.responses.IntegrationResponse
import com.github.tesis.integrationms.exception.ClientException
import com.github.tesis.integrationms.repositories.IntegrationRepository
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class IntegrationService {

    @Autowired
    lateinit var integrationRepository: IntegrationRepository

    @Autowired
    lateinit var runnerClient: RunnerClient

    fun createIntegration(createIntegrationRequest: CreateIntegrationRequest, userCode: UUID): IntegrationResponse {
        val integration = createIntegrationRequest.toIntegration(userCode)
        val schema = runnerClient.generateSchema(integration.toSchemaRequest(), userCode)
        if (schema.statusCodeValue != 200) {
            throw ClientException(ErrorCodes.DATABASE_CONNECTION_FAILED, listOf("Database connection failed"))
        }
        integration.schema = schema.body
        integration.checksum = DigestUtils.sha256Hex(jacksonObjectMapper().writeValueAsString(schema.body))
        integrationRepository.save(integration)

        return integration.toResponse()
    }

    fun listIntegrations(userCode: UUID): List<IntegrationResponse> {
        val integrations = integrationRepository.getByUserCode(userCode)
        val integrationsResponse = mutableListOf<IntegrationResponse>()
        integrations.forEach {
            integrationsResponse.add(it.toResponse())
        }

        return integrationsResponse
    }

    fun getSchema(integrationCode: UUID, userCode: UUID): Any {
        val integration = integrationRepository.getByCodeAndUserCode(integrationCode, userCode).orElseThrow {
            ClientException(ErrorCodes.INTEGRATION_NOT_FOUND, listOf())
        }

        return integration.schema ?: throw ClientException(ErrorCodes.SCHEMA_EMPTY, listOf("The schema from integration is empty"))
    }

    fun getIntegrationDetail(integrationCode: UUID, userCode: UUID): IntegrationDetailResponse {
        val integration = integrationRepository.getByCodeAndUserCode(integrationCode, userCode).orElseThrow {
            ClientException(ErrorCodes.INTEGRATION_NOT_FOUND, listOf())
        }

        return integration.toDetailResponse()
    }

    fun updateIntegration(updateIntegrationRequest: UpdateIntegrationRequest, integrationCode: UUID, userCode: UUID): IntegrationDetailResponse {
        val integration = integrationRepository.getByCodeAndUserCode(integrationCode, userCode).orElseThrow {
            ClientException(ErrorCodes.INTEGRATION_NOT_FOUND, listOf())
        }

        integration.updateFromRequest(updateIntegrationRequest)
        updateIntegrationRequest.config?.let {
            val schema = runnerClient.generateSchema(integration.toSchemaRequest(), userCode)
            integration.schema = schema.body
            integration.checksum = DigestUtils.sha256Hex(jacksonObjectMapper().writeValueAsString(schema.body))
        }
        integrationRepository.save(integration)

        return integration.toDetailResponse()
    }

    fun deleteIntegration(integrationCode: UUID, userCode: UUID): IntegrationDetailResponse {
        val integration = integrationRepository.getByCodeAndUserCode(integrationCode, userCode).orElseThrow {
            ClientException(ErrorCodes.INTEGRATION_NOT_FOUND, listOf())
        }

        integrationRepository.delete(integration)
        return integration.toDetailResponse()
    }
}

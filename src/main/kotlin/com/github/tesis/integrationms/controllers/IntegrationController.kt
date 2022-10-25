package com.github.tesis.integrationms.controllers

import com.github.tesis.integrationms.constants.HEADER_USER
import com.github.tesis.integrationms.constants.Routes
import com.github.tesis.integrationms.domain.requests.CreateIntegrationRequest
import com.github.tesis.integrationms.domain.requests.UpdateIntegrationRequest
import com.github.tesis.integrationms.domain.responses.IntegrationDetailResponse
import com.github.tesis.integrationms.domain.responses.IntegrationResponse
import com.github.tesis.integrationms.services.IntegrationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("${Routes.Integration.V1}${Routes.Integration.INTEGRATION}")
class IntegrationController {

    @Autowired
    lateinit var integrationService: IntegrationService

    @PostMapping
    fun createIntegration(
        @RequestBody createIntegrationRequest: CreateIntegrationRequest,
        @RequestHeader(HEADER_USER) userCode: UUID
    ): ResponseEntity<IntegrationResponse> {
        return ResponseEntity(integrationService.createIntegration(createIntegrationRequest, userCode), HttpStatus.CREATED)
    }

    @GetMapping
    fun listIntegrations(
        @RequestHeader(HEADER_USER) userCode: UUID
    ): ResponseEntity<List<IntegrationResponse>> =
        ResponseEntity(integrationService.listIntegrations(userCode), HttpStatus.OK)

    @GetMapping(value = [Routes.Integration.GET_SCHEMA])
    fun getSchema(
        @RequestHeader(HEADER_USER) userCode: UUID,
        @PathVariable("integration_code") integrationCode: UUID
    ): ResponseEntity<Any> =
        ResponseEntity(integrationService.getSchema(integrationCode, userCode), HttpStatus.OK)

    @GetMapping(value = [Routes.Integration.GET_BY_CODE])
    fun getIntegration(
        @RequestHeader(HEADER_USER) userCode: UUID,
        @PathVariable("integration_code") integrationCode: UUID
    ): ResponseEntity<IntegrationDetailResponse> =
        ResponseEntity(integrationService.getIntegrationDetail(integrationCode, userCode), HttpStatus.OK)

    @PatchMapping(value = [Routes.Integration.GET_BY_CODE])
    fun updateIntegration(
        @RequestHeader(HEADER_USER) userCode: UUID,
        @PathVariable("integration_code") integrationCode: UUID,
        @RequestBody updateIntegrationRequest: UpdateIntegrationRequest,
    ): ResponseEntity<IntegrationDetailResponse> =
        ResponseEntity(integrationService.updateIntegration(updateIntegrationRequest, integrationCode, userCode), HttpStatus.OK)

    @DeleteMapping(value = [Routes.Integration.GET_BY_CODE])
    fun deleteIntegration(
        @RequestHeader(HEADER_USER) userCode: UUID,
        @PathVariable("integration_code") integrationCode: UUID
    ): ResponseEntity<IntegrationDetailResponse> =
        ResponseEntity(integrationService.deleteIntegration(integrationCode, userCode), HttpStatus.OK)
}

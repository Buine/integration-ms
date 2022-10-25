package com.github.tesis.integrationms.controllers

import com.github.tesis.integrationms.BaseTest
import com.github.tesis.integrationms.constants.Routes
import com.github.tesis.integrationms.mocks.RunnerClientMock
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockserver.client.MockServerClient
import org.mockserver.junit.jupiter.MockServerSettings
import org.mockserver.model.HttpStatusCode
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.UUID

@SpringBootTest
@AutoConfigureMockMvc
@MockServerSettings(ports = [1080], perTestSuite = true)
class IntegrationControllerTest : BaseTest() {

    @Test
    fun `Create a integration`(mockServer: MockServerClient) {
        RunnerClientMock.generateSchema(
            HttpStatusCode.OK_200,
            readJsonFile("external/responses/schema_response_200.json"),
            mockServer
        )
        mockPost(
            url = "${Routes.Integration.V1}${Routes.Integration.INTEGRATION}",
            body = readJsonFile("requests/create_integration.json"),
            userCode = UUID.randomUUID()
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andReturn()

        val integration = integrationRepository.findAll().first()

        Assertions.assertEquals("Test Integration", integration.name)
    }

    @Test
    fun `Create a failed integration`(mockServer: MockServerClient) {
        RunnerClientMock.generateSchema(
            HttpStatusCode.BAD_REQUEST_400,
            readJsonFile("external/responses/schema_response_200.json"),
            mockServer
        )
        mockPost(
            url = "${Routes.Integration.V1}${Routes.Integration.INTEGRATION}",
            body = readJsonFile("requests/create_integration.json"),
            userCode = UUID.randomUUID()
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.content().json(readJsonFile("responses/error_connect_runner.json")))
            .andReturn()
    }
}

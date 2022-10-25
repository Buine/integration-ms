package com.github.tesis.integrationms

import com.github.tesis.integrationms.constants.HEADER_USER
import com.github.tesis.integrationms.repositories.IntegrationRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockserver.client.MockServerClient
import org.mockserver.junit.jupiter.MockServerExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.util.UUID

@ActiveProfiles("local")
@ExtendWith(MockServerExtension::class)
open class BaseTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    protected lateinit var integrationRepository: IntegrationRepository

    @BeforeEach
    fun prepareData(mockServer: MockServerClient) {
        integrationRepository.deleteAll()
        mockServer.reset()
    }

    protected fun mockGet(url: String): ResultActions =
        mockMvc.perform(
            MockMvcRequestBuilders
                .get(url)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )

    protected fun mockPost(url: String, body: String, userCode: UUID): ResultActions =
        mockMvc.perform(
            MockMvcRequestBuilders
                .post(url)
                .accept(MediaType.APPLICATION_JSON)
                .header(HEADER_USER, userCode)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

    protected fun mockPatch(url: String, body: String = ""): ResultActions =
        mockMvc.perform(
            MockMvcRequestBuilders
                .patch(url)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

    protected fun readJsonFile(filePath: String): String =
        javaClass.classLoader.getResourceAsStream(filePath).reader().readText()
}

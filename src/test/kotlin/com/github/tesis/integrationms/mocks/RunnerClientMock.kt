package com.github.tesis.integrationms.mocks

import com.github.tesis.integrationms.constants.ExternalRoutes
import org.mockserver.client.MockServerClient
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.mockserver.model.HttpStatusCode
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import java.nio.charset.StandardCharsets

object RunnerClientMock {
    fun generateSchema(
        httpStatusCode: HttpStatusCode,
        response: String,
        mockServer: MockServerClient
    ) {
        val path = ExternalRoutes.Runner.REQUEST_SCHEMA

        mockServer.`when`(
            HttpRequest.request()
                .withMethod(HttpMethod.POST.name)
                .withHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .withPath(path)
        )
            .respond(
                HttpResponse.response()
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .withStatusCode(httpStatusCode.code())
                    .withBody(response, StandardCharsets.UTF_8)
            )
    }
}

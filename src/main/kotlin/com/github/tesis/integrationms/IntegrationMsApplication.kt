package com.github.tesis.integrationms

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class IntegrationMsApplication

fun main(args: Array<String>) {
    runApplication<IntegrationMsApplication>(*args)
}

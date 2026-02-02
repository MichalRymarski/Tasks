#!/usr/bin/env kotlin

@file:CompilerOptions("-jvm-target=17")
@file:DependsOn(
    "org.springframework:spring-web:6.1.2",
    "com.fasterxml.jackson.module:jackson-module-kotlin:2.16.1",
)

import org.springframework.web.client.RestClient
import org.springframework.web.client.body

val baseUrl = "http://localhost:8080"
val client = RestClient.create(baseUrl) ?: throw IllegalStateException("Failed to create RestClient")
val response = client.post()
    .uri("/task")
    .retrieve()
    .body<String>()

println("Response: $response")


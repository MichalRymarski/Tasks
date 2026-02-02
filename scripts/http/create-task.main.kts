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
var response = client.post()
    .uri("/task")
    .retrieve()
    .body<String>()

println("Response on post: $response")

var listResponse = client.get()
    .uri("/task")
    .retrieve()
    .body<List<String>>()
println("Response on get all: $listResponse")

response = client.get()
    .uri("/task/1")
    .retrieve()
    .body<String>()
println("Response on get by id: $response")

response = client.put()
    .uri("/task/1")
    .body("Updated Task 1")
    .retrieve()
    .body<String>()
println("Response on put: $response")

response = client.patch()
    .uri("/task/1")
    .body("Completed")
    .retrieve()
    .body<String>()
println("Response on patch: $response")
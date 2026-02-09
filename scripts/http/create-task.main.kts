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

// POST - no body, returns 201 with no content
data class TaskDto(
    val title: String? = null,
    val description: String ? = null,
)
try {
    val postResponse = client
        .post()
        .uri("/task")
        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
        .body(TaskDto(title = "test1", description = "test"))
        .retrieve()
        .toBodilessEntity()
    println("Response on post: ${postResponse.statusCode} with body ${postResponse.body}")
} catch (e: org.springframework.web.client.HttpClientErrorException) {
    println("Error status: ${e.statusCode}")
    println("Error body: ${e.responseBodyAsString}")  // This will show you what's actually returned
}

// GET all - returns List<TaskDto> as JSON
val listResponse = client.get()
    .uri("/task")
    .retrieve()
    .body<List<Map<String, Any>>>()
println("Response on get all: $listResponse")

// GET by id - returns TaskDto as JSON
val getResponse = client.get()
    .uri("/task/1")
    .retrieve()
    .body<Map<String, Any>>()
println("Response on get by id: $getResponse")

// PUT - body is a TaskStatus enum value, returns TaskDto
val putResponse = client.put()
    .uri("/task/1")
    .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
    .body(TaskDto(title = "asd", description = "test"))
    .retrieve()
    .toBodilessEntity()
println("Response on put: $putResponse")

// PATCH - body is a TaskStatus enum value, returns TaskDto
val patchResponse = client.patch()
    .uri("/task/1")
    .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
    .body("\"TODO\"")
    .retrieve()
    .body<Map<String, Any>>()
println("Response on patch: $patchResponse")
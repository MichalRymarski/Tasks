#!/usr/bin/env kotlin

@file:CompilerOptions("-jvm-target=17")
@file:DependsOn(
    "org.springframework:spring-web:6.1.2",
    "com.fasterxml.jackson.module:jackson-module-kotlin:2.16.1",
)

import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

val baseUrl = "http://localhost:8080"
val client = RestClient.create(baseUrl) ?: throw IllegalStateException("Failed to create RestClient")
data class UserRegistrationDto(val firstName: String, val lastName: String, val email: String, val password: String)
data class UserLoginDto(val email: String, val password: String)

val testRegister = UserRegistrationDto(
    firstName = "Test",
    lastName = "User",
    email = "test.user@example.com",
    password = "password123"
)

val testLogin = UserLoginDto(
    email = "test.user@example.com",
    password = "password123"
)

try {
    val registerResponse = client.post()
        .uri("/register")
        .contentType(APPLICATION_JSON)
        .body(testRegister)
        .retrieve()
        .toBodilessEntity()
    println("User registered: ${registerResponse.statusCode}")
} catch (e: HttpClientErrorException) {
    println("Registration failed: ${e.statusCode}")
    println("Body: ${e.responseBodyAsString}")
}


data class TaskDto(
    val title: String? = null,
    val description: String ? = null,
)
try {
    val preLoginGet = client.get()
        .uri("/task")
        .retrieve()
        .body<List<Map<String, Any>>>()
    println("GET before login succeeded unexpectedly: $preLoginGet")
} catch (e: HttpClientErrorException) {
    println("GET before login failed as expected: ${e.statusCode}")
    println("Body: ${e.responseBodyAsString}")
}


try {
    val loginResponse = client.post()
        .uri("/login")
        .contentType(APPLICATION_JSON)
        .body(testLogin)
        .retrieve()
        .toBodilessEntity()
    println("User logged in: ${loginResponse.statusCode}")
} catch (e: HttpClientErrorException) {
    println("Login failed: ${e.statusCode}")
    println("Body: ${e.responseBodyAsString}")
}

// POST - no body, returns 201 with no content

try {
    val postResponse = client
        .post()
        .uri("/task")
        .contentType(APPLICATION_JSON)
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
    .contentType(APPLICATION_JSON)
    .body(TaskDto(title = "asd", description = "test"))
    .retrieve()
    .toBodilessEntity()
println("Response on put: $putResponse")

// PATCH - body is a TaskStatus enum value, returns TaskDto
val patchResponse = client.patch()
    .uri("/task/1")
    .contentType(APPLICATION_JSON)
    .body("\"TODO\"")
    .retrieve()
    .body<Map<String, Any>>()
println("Response on patch: $patchResponse")
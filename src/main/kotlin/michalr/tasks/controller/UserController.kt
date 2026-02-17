package michalr.tasks.controller

import org.springframework.web.bind.annotation.RestController
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import michalr.tasks.dto.UserLoginFrontendDto
import michalr.tasks.dto.UserRegistrationFrontendDto
import michalr.tasks.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/")
class UserController(
    private val userService: UserService
) {
    private val log = KotlinLogging.logger {}

    @PostMapping("/register")
    fun registerUser(@RequestBody @Valid registrationDto: UserRegistrationFrontendDto): ResponseEntity<String> {
        log.info { "Received request to register a new user" }
        userService.registerSystemUser(registrationDto.toDomain())

        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PostMapping("/login")
    fun loginUser(@RequestBody @Valid loginDto: UserLoginFrontendDto): ResponseEntity<String> {
        log.info { "Received request to login a user with email ${loginDto.email} and password ${loginDto.password}" }
        userService.loginSystemUser(loginDto.toDomain())

        return ResponseEntity.ok().build()
    }
}

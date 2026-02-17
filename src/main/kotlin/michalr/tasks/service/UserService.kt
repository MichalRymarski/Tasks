package michalr.tasks.service

import org.springframework.stereotype.Service
import io.github.oshai.kotlinlogging.KotlinLogging
import michalr.tasks.data.role.AppUser
import michalr.tasks.dto.UserLoginDomainDto
import michalr.tasks.dto.UserRegistrationDomainDto
import michalr.tasks.exception.custom.UserAlreadyExistsException
import michalr.tasks.repository.RoleRepository
import michalr.tasks.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val roleRepository: RoleRepository,
    private val authenticationManager: AuthenticationManager
) {

    private val log = KotlinLogging.logger {}

    fun registerSystemUser(registrationDto: UserRegistrationDomainDto): AppUser {
        if (userRepository.findByEmail(registrationDto.email) != null) {
            throw UserAlreadyExistsException("User with email ${registrationDto.email} already exists")
        }

        val user = registrationDto.toSystemUser(passwordEncoder,roleRepository)

        return userRepository.save(user)
    }


    fun loginSystemUser(userDto: UserLoginDomainDto) {
         try {
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    userDto.email,
                    userDto.password
                )
            )

            SecurityContextHolder.getContext().authentication = authentication
            log.info { "User ${userDto.email} logged in successfully" }
        } catch (e: BadCredentialsException) {
            log.warn { "Failed login attempt for user: ${userDto.email} pass : ${userDto.password}. Cause: ${e.message}" }
            throw BadCredentialsException("Invalid email or password")
        }
    }
}

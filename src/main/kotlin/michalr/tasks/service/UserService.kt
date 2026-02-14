package michalr.tasks.service

import org.springframework.stereotype.Service
import io.github.oshai.kotlinlogging.KotlinLogging
import michalr.tasks.data.role.AppUser
import michalr.tasks.dto.UserLoginDomainDto
import michalr.tasks.dto.UserRegistrationDomainDto
import michalr.tasks.exception.custom.UserAlreadyExistsException
import michalr.tasks.repository.RoleRepository
import michalr.tasks.repository.UserRepository
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.password.PasswordEncoder

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val roleRepository: RoleRepository,
    private val taskUserDetailsService: TaskUserDetailsService
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
        val userDetails = taskUserDetailsService.loadUserByUsername(userDto.email)

        if (!passwordEncoder.matches(userDto.password, userDetails.password)) {
            throw BadCredentialsException("Invalid email or password")
        }
    }
}

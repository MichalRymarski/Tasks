package michalr.tasks.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import michalr.tasks.data.role.AppUser
import michalr.tasks.repository.RoleRepository
import michalr.tasks.util.encodeValidated
import org.hibernate.validator.constraints.Length
import org.springframework.security.crypto.password.PasswordEncoder
import java.io.Serializable

data class UserRegistrationFrontendDto(
    @NotBlank(message = "First name can't be blank") @Length(
        message = "First name must be between 1 and 20 characters long",
        min = 1,
        max = 20
    )
    val firstName: String = "",
    @NotBlank(message = "Last name can't be blank") @Length(
        message = "Last name must be between 1 and 40 characters long",
        min = 1,
        max = 40
    )
    val lastName: String = "",
    @Email @NotBlank
    val email: String = "",
    @NotBlank @Length(message = "Password must be between 1 and 100 characters long", min = 1, max = 100)
    val password: String = ""
) : Serializable {
    fun toDomain(): UserRegistrationDomainDto {
        return UserRegistrationDomainDto(
            firstName = firstName.trim(),
            lastName = lastName.trim(),
            email = email.trim(),
            password = password
        )
    }
}

data class UserRegistrationDomainDto(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
) {
    fun toSystemUser(passwordEncoder: PasswordEncoder, roleRepository: RoleRepository): AppUser {
        val dto = this
        return AppUser().apply {
            firstName = dto.firstName
            lastName = dto.lastName
            email = dto.email
            password = passwordEncoder.encodeValidated(dto.password)
            enabled = true
            tokenExpired = false
            roles = mutableSetOf(roleRepository.findByName("ROLE_USER")!!)
        }
    }
}

data class UserLoginFrontendDto(
    @Email
    @NotBlank(message = "Email can't be blank")
    val email: String = "",
    @NotBlank(message = "Password can't be blank")
    @Length(message = "Password must be between 1 and 100 characters long", min = 1, max = 100)
    val password: String = ""
) : Serializable {
    fun toDomain(): UserLoginDomainDto {
        return UserLoginDomainDto(
            email = email.trim(),
            password = password
        )
    }
}

data class UserLoginDomainDto(
    val email: String,
    val password: String
)

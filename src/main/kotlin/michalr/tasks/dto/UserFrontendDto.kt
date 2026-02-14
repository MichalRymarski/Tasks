package michalr.tasks.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import michalr.tasks.data.role.TaskUser
import org.hibernate.validator.constraints.Length
import java.io.Serializable

data class UserFrontendDto(
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
    fun toDomain(): UserDomainDto {
        return UserDomainDto(
            firstName = firstName.trim(),
            lastName = lastName.trim(),
            email = email.trim(),
            password = password
        )
    }

    fun toUser(): TaskUser {
        return this.toDomain().toUser()
    }
}

data class UserDomainDto(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
) {
    fun toUser(): TaskUser {
        val user = TaskUser()
        user.firstName = firstName
        user.lastName = lastName
        user.email = email
        user.password = password

        return user
    }
}

package michalr.tasks.util

import org.springframework.security.crypto.password.PasswordEncoder

/**
 * Extension function for PasswordEncoder to encode a raw password without null checks.
 * Null raw passwords will never enter this because Jakarta Validation won't allow DTO to be created with null password, and the DTO is the only way to create a user.
 */
fun PasswordEncoder.encodeValidated(rawPassword: String): String {
    return this.encode(rawPassword)!!
}
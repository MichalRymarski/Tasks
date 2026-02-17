package michalr.tasks.exception.handler

import michalr.tasks.controller.UserController
import michalr.tasks.exception.custom.UserAlreadyExistsException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice(assignableTypes = [UserController::class])
class UserControllerAdvice {
    @ExceptionHandler(UserAlreadyExistsException::class)
    fun handleUserAlreadyExists(exception: UserAlreadyExistsException): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            mapOf(
                "message" to exception.message.orEmpty()
            )
        )
    }

    @ExceptionHandler(UsernameNotFoundException::class)
    fun handleUsernameNotFound(exception: UsernameNotFoundException): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            mapOf(
                "message" to exception.message.orEmpty()
            )
        )
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentials(exception: BadCredentialsException): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            mapOf(
                "message" to exception.message.orEmpty()
            )
        )
    }
}
package michalr.tasks.exception.handler

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.annotation.Priority
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
/*

@ControllerAdvice
@Priority(Ordered.LOWEST_PRECEDENCE)
class GenericExceptionHandler {

    private val log = KotlinLogging.logger {}

    @ExceptionHandler(Exception::class)
    fun handleGenericException(exception: Exception): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            mapOf(
                "message" to "An unexpected error occurred: ${exception.message}"
            )
        )
    }
}*/

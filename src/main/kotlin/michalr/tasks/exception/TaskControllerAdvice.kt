package michalr.tasks.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class TaskControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationErrors(exception: MethodArgumentNotValidException): ResponseEntity<Map<String, Any?>> {
        val errors = exception.bindingResult.fieldErrors.associate {
            it.field to it.defaultMessage
        }

        return ResponseEntity.badRequest().body(
            mapOf(
                "message" to "Validation failed",
                "errors" to errors
            )
        )
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun handleMissingElement(exception: NoSuchElementException): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(404).body(
            mapOf(
                "message" to exception.message.orEmpty()
            )
        )
    }
}

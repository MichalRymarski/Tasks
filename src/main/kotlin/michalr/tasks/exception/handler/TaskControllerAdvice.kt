package michalr.tasks.exception.handler

import michalr.tasks.controller.TaskController
import michalr.tasks.exception.custom.TaskAlreadyExistsException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice(assignableTypes = [TaskController::class])
class TaskControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationErrors(exception: MethodArgumentNotValidException): ResponseEntity<Map<String, Any?>> {
        val errors = exception.bindingResult.fieldErrors.associate {
            it.field to it.defaultMessage
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            mapOf(
                "message" to "Validation failed",
                "errors" to errors
            )
        )
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun handleMissingElement(exception: NoSuchElementException): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            mapOf(
                "message" to exception.message.orEmpty()
            )
        )
    }

    @ExceptionHandler(TaskAlreadyExistsException::class)
    fun handleTaskAlreadyExists(exception: TaskAlreadyExistsException): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            mapOf(
                "message" to exception.message.orEmpty()
            )
        )
    }
}
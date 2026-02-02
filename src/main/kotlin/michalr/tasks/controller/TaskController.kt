package michalr.tasks.controller

import org.springframework.web.bind.annotation.RestController
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/task")
class TaskController {

    private val log = KotlinLogging.logger {}

    @GetMapping
    fun getAllTasks(): ResponseEntity<List<String>> {
        log.info { "Received request to get all tasks" }

        return ResponseEntity.ok(listOf("task1", "task2"))
    }

    @GetMapping("/{id} ")
    fun getTaskById(@PathVariable id: Int): ResponseEntity<String> {
        log.info { "Received request to get task with id: $id" }

        return ResponseEntity.ok("task with id: $id")
    }

    @PostMapping
    fun createTask(): ResponseEntity<String> {
        log.info { "Received request to create a new task" }

        return ResponseEntity.ok("new task created")
    }

    @PutMapping("/{id} ")
    fun updateTask(@PathVariable id: Int, @RequestBody task: String): ResponseEntity<String> {
        log.info { "Received request to update task with id: $id  with $task" }
        return ResponseEntity.ok("task with id: $id updated to $task")
    }

    @PatchMapping("/{id} ")
    fun changeTaskStatus(@PathVariable id: Int, @RequestBody status: String): ResponseEntity<String> {
        log.info { "Received request to partially update status with id: $id  with $status" }
        return ResponseEntity.ok("status with id: $id partially updated to $status")
    }
}

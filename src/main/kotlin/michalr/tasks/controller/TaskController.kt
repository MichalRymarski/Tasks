package michalr.tasks.controller

import org.springframework.web.bind.annotation.RestController
import io.github.oshai.kotlinlogging.KotlinLogging
import michalr.tasks.service.TaskService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
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
class TaskController(
    val taskService: TaskService
) {

    private val log = KotlinLogging.logger {}

    @GetMapping
    fun getAllTasks(): ResponseEntity<List<String>> {
        log.info { "Received request to get all tasks" }
        val tasks = taskService.getAllTasks()

        return ResponseEntity.ok(tasks)
    }

    @GetMapping("/{id}")
    fun getTaskById(@PathVariable id: Int): ResponseEntity<String> {
        log.info { "Received request to get task with id: $id" }
        val task = taskService.getTaskById(id)

        return ResponseEntity.ok(task)
    }

    @PostMapping
    fun createTask(): ResponseEntity<Void> {
        log.info { "Received request to create a new task" }
        taskService.createNewTask()

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build()
    }

    @PutMapping("/{id}")
    fun updateTask(@PathVariable id: Int, @RequestBody task: String): ResponseEntity<String> {
        log.info { "Received request to update task with id: $id  with $task" }
        val updatedTask = taskService.updateTask(id, task)

        return ResponseEntity.ok(task)
    }

    @PatchMapping("/{id}")
    fun changeTaskStatus(@PathVariable id: Int, @RequestBody status: String): ResponseEntity<String> {
        log.info { "Received request to partially update status with id: $id  with $status" }
        val updatedStatus = taskService.changeTaskStatus(id, status)

        return ResponseEntity.ok(updatedStatus)
    }
}

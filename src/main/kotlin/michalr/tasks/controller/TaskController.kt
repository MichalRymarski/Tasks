package michalr.tasks.controller

import org.springframework.web.bind.annotation.RestController
import io.github.oshai.kotlinlogging.KotlinLogging
import michalr.tasks.data.Task
import michalr.tasks.data.TaskStatus
import michalr.tasks.dto.TaskDto
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
    fun getAllTasks(): ResponseEntity<List<TaskDto>> {
        log.info { "Received request to get all tasks" }
        val tasks = taskService.getAllTasks().map { task -> task.toDto() }

        return ResponseEntity.ok(tasks)
    }

    @GetMapping("/{id}")
    fun getTaskById(@PathVariable id: Int): ResponseEntity<TaskDto> {
        log.info { "Received request to get task with id: $id" }
        val task = taskService.getTaskById(id).toDto()

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
    fun updateTask(@PathVariable id: Int, @RequestBody status: TaskStatus): ResponseEntity<TaskDto> {
        log.info { "Received request to update task with id: $id  with $status" }
        val updatedTask = taskService.changeTaskStatus(id, status).toDto()

        return ResponseEntity.ok(updatedTask)
    }

    @PatchMapping("/{id}")
    fun changeTaskStatus(@PathVariable id: Int, @RequestBody status: TaskStatus): ResponseEntity<TaskDto> {
        log.info { "Received request to partially update status with id: $id  with $status" }
        val updatedStatusTask = taskService.changeTaskStatus(id, status).toDto()

        return ResponseEntity.ok(updatedStatusTask)
    }
}

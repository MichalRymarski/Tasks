package michalr.tasks.controller

import org.springframework.web.bind.annotation.RestController
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import michalr.tasks.data.TaskStatus
import michalr.tasks.dto.TaskFrontendDto
import michalr.tasks.service.TaskService
import org.springframework.http.HttpStatus
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
    fun getAllTasks(): ResponseEntity<List<TaskFrontendDto>> {
        log.info { "Received request to get all tasks" }
        val tasks = taskService.getAllTasks().map { task -> task.toDto() }

        return ResponseEntity.ok(tasks)
    }

    @GetMapping("/{id}")
    fun getTaskById(@PathVariable id: Int): ResponseEntity<TaskFrontendDto> {
        log.info { "Received request to get task with id: $id" }
        val task = taskService.getTaskById(id).toDto()

        return ResponseEntity.ok(task)
    }

    @PostMapping
    fun createTask(@RequestBody  @Valid taskDto : TaskFrontendDto): ResponseEntity<Void> {
        log.info { "Received request to create a new task" }
        taskService.createNewTask(taskDto.toTask())

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build()
    }

    @PutMapping("/{id}")
    fun updateTask(@PathVariable id: Int, @RequestBody @Valid taskDto: TaskFrontendDto): ResponseEntity<TaskFrontendDto> {
        log.info { "Received request to update task with id: $id  with $taskDto" }
        val updatedTask = taskService.updateTask(id, taskDto.toTask()).toDto()

        return ResponseEntity.ok(updatedTask)
    }

    @PatchMapping("/{id}")
    fun changeTaskStatus(@PathVariable id: Int, @RequestBody status: TaskStatus): ResponseEntity<TaskFrontendDto> {
        log.info { "Received request to partially update status with id: $id  with $status" }
        val updatedStatusTask = taskService.changeTaskStatus(id, status).toDto()

        return ResponseEntity.ok(updatedStatusTask)
    }
}

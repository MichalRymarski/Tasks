package michalr.tasks.service

import org.springframework.stereotype.Service
import io.github.oshai.kotlinlogging.KotlinLogging
import michalr.tasks.data.Task
import michalr.tasks.data.TaskStatus
import michalr.tasks.repository.TaskRepository

@Service
class TaskService(val taskRepository: TaskRepository) {

    private val log = KotlinLogging.logger {}

    fun getAllTasks(): List<Task> {
        log.info { "Fetching all tasks from the service layer" }
        val tasks = taskRepository.findAll()

        return tasks
    }

    fun getTaskById(id: Int): Task {
        log.info { "Fetching task with id: $id from the service layer" }
        val task = taskRepository.findById(id).orElseThrow { NoSuchElementException("Task with id $id not found") }

        return task
    }

    fun createNewTask(){
        log.info { "Creating a new task in the service layer" }
        val newTask = Task().apply {
            title = "New Task"
            description = "This is a new task"
        }
        taskRepository.save(newTask)
    }

    fun updateTask(id: Int, task: Task): Task {
        log.info { "Updating task with id: $id to $task in the service layer" }
        val updatedTask = taskRepository.updateRecord(id, task)

        return updatedTask
    }

    fun changeTaskStatus(id: Int, status: TaskStatus): Task {
        log.info { "Changing status of task with id: $id to $status in the service layer" }
        val updatedTask = taskRepository.updateStatus(id, status)

        return updatedTask
    }
}

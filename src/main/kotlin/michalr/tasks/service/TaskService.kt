package michalr.tasks.service

import org.springframework.stereotype.Service
import io.github.oshai.kotlinlogging.KotlinLogging

@Service
class TaskService {

    private val log = KotlinLogging.logger {}

    fun getAllTasks(): List<String> {
        log.info { "Fetching all tasks from the service layer" }
        return listOf("task1", "task2")
    }

    fun getTaskById(id: Int): String {
        log.info { "Fetching task with id: $id from the service layer" }
        return "task with id: $id"
    }

    fun createNewTask(){
        log.info { "Creating a new task in the service layer" }
        //will throw exception if something goes wrong
    }

    fun updateTask(id: Int, task: String): String {
        log.info { "Updating task with id: $id to $task in the service layer" }
        return "task with id: $id updated to $task"
    }

    fun changeTaskStatus(id: Int, status: String): String {
        log.info { "Changing status of task with id: $id to $status in the service layer" }
        return "status with id: $id changed to $status"
    }
}

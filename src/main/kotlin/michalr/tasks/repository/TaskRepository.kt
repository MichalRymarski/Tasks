package michalr.tasks.repository

import org.springframework.stereotype.Repository
import io.github.oshai.kotlinlogging.KotlinLogging
import michalr.tasks.data.Task
import michalr.tasks.data.TaskStatus
import org.springframework.data.jpa.repository.JpaRepository

@Repository
interface TaskRepository : JpaRepository<Task, Int> {

    fun updateRecord(id: Int, task: Task): Task {
        val foundTask = findById(id).orElseThrow { NoSuchElementException("Task with id $id not found")  }
        foundTask.apply {
            title = task.title
            description = task.description
            status = task.status
        }

        return save(foundTask)
    }

    fun updateStatus(id: Int, status: TaskStatus): Task {
        val foundTask = findById(id).orElseThrow { NoSuchElementException("Task with id $id not found")  }
        foundTask.status = status

        return save(foundTask)
    }
}

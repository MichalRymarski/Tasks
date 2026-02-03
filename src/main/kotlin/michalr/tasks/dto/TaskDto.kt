package michalr.tasks.dto

import michalr.tasks.data.Task
import michalr.tasks.data.TaskStatus
import java.io.Serializable


data class TaskDto(val title: String = "", val description: String = "", val status: TaskStatus? = null) : Serializable {
    fun toTask(): Task {
        val task = Task()
        task.title = title
        task.description = description
        status?.let { task.status = it }

        return task
    }
}

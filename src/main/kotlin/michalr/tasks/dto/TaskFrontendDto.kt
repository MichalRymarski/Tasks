package michalr.tasks.dto

import jakarta.validation.constraints.NotBlank
import michalr.tasks.data.Task
import michalr.tasks.data.TaskStatus
import java.io.Serializable


data class TaskFrontendDto(
    @NotBlank(message = "Title must not be blank")
    val title: String? = null,
    val description: String? = null,
    val status: TaskStatus? = null
) : Serializable {
    fun toTask(): Task {
        return this.toDomain().toTask()
    }

    fun toDomain(): TaskDomainDto {
        return TaskDomainDto(
            title = title ?: "",
            description = description,
            status = status
        )
    }
}

data class TaskDomainDto(
    val title: String,
    val description: String? = null,
    val status: TaskStatus? = null
) {
    fun toTask(): Task {
        val task = Task()
        task.title = title
        description?.let{task.description = it}
        status?.let { task.status = it }

        return task
    }
}

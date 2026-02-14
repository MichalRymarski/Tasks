package michalr.tasks.data

import jakarta.persistence.*
import michalr.tasks.dto.TaskFrontendDto

@Entity
@Table(name = "task")
class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @Column(name = "title", nullable = false, unique = true)
    var title: String = ""

    @Column(name = "description", nullable = true, columnDefinition = "TEXT")
    var description: String = ""

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    var status: TaskStatus = TaskStatus.TODO

    fun toDto(): TaskFrontendDto {
        return TaskFrontendDto(
            title = this.title,
            description = this.description,
            status = this.status
        )
    }
}
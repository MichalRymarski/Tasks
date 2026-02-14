package michalr.tasks.repository

import michalr.tasks.data.role.TaskUser
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<TaskUser, Long> {
    fun findByEmail(email: String): TaskUser?
}
package michalr.tasks.repository

import michalr.tasks.data.role.AppUser
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<AppUser, Long> {
    fun findByEmail(email: String): AppUser?

}
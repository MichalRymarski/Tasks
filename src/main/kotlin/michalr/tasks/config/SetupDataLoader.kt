package michalr.tasks.config

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.transaction.Transactional
import michalr.tasks.data.role.Privilege
import michalr.tasks.data.role.Role
import michalr.tasks.data.role.AppUser
import michalr.tasks.repository.PrivilegeRepository
import michalr.tasks.repository.RoleRepository
import michalr.tasks.repository.UserRepository
import michalr.tasks.util.encodeValidated
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class SetupDataLoader(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val privilegeRepository: PrivilegeRepository,
    private val passwordEncoder: PasswordEncoder
) : ApplicationListener<ContextRefreshedEvent> {
    private var alreadySetup = false
    private val log = KotlinLogging.logger {}

    @Transactional
    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        if (alreadySetup) return

        val readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE")
        val writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE")
        val adminPrivileges = mutableSetOf(readPrivilege, writePrivilege)
        val userPrivileges = mutableSetOf(readPrivilege)

        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges)
        createRoleIfNotFound("ROLE_USER", userPrivileges)
        setupAdmin()

        alreadySetup = true
    }

    @Transactional
    fun createPrivilegeIfNotFound(name: String): Privilege {
        var privilege: Privilege? = privilegeRepository.findByName(name)
        if (privilege == null) {
            privilege = Privilege()
            privilege.name = name
            privilegeRepository.save(privilege)
        }

        return privilege
    }

    @Transactional
    fun createRoleIfNotFound(
        name: String, privileges: MutableSet<Privilege>
    ): Role {
        var role: Role? = roleRepository.findByName(name)
        if (role == null) {
            role = Role()
            role.name = name
            role.privileges = privileges
            roleRepository.save(role)
        }

        return role
    }

    private fun setupAdmin() {
        val adminRole = roleRepository.findByName("ROLE_ADMIN")
        val user = AppUser().apply {
            firstName = "Test"
            lastName = "Test"
            password = passwordEncoder.encodeValidated("test")
            email = "test@test.com"
            roles = if(adminRole != null) mutableSetOf(adminRole) else mutableSetOf()
            enabled = true
        }

        userRepository.save(user)
    }
}
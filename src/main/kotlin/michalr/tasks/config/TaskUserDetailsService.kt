package michalr.tasks.config

import org.springframework.stereotype.Service
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.transaction.Transactional
import michalr.tasks.data.role.Privilege
import michalr.tasks.data.role.Role
import michalr.tasks.repository.RoleRepository
import michalr.tasks.repository.UserRepository
import org.springframework.context.MessageSource
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder

@Service("userDetailsService")
@Transactional
class TaskUserDetailsService(
    private val userRepository: UserRepository,
) : UserDetailsService {
    private val log = KotlinLogging.logger {}

    override fun loadUserByUsername(email: String): UserDetails? {
        val user = userRepository.findByEmail(email) ?: throw UsernameNotFoundException("User not found with email: $email") //todo : add to controller advice on login
        log.info { "Found User with email: $email" }

        return User(
            user.email,
            user.password,
            user.enabled,
            true,
            true,
            !user.tokenExpired,
            getAuthorities(user.roles)
        )
    }

    private fun getAuthorities(roles: MutableSet<Role>): List<GrantedAuthority> {
        return getGrantedAuthorities(getPrivileges(roles))
    }

    private fun getPrivileges(roles: MutableSet<Role>): List<String> {
        val privileges = mutableListOf<String>()
        val collection = mutableSetOf<Privilege>()

        for (role in roles) {
            privileges.add(role.name)
            collection.addAll(role.privileges)
        }
        for (item in collection) {
            privileges.add(item.name)
        }

        return privileges
    }

    private fun getGrantedAuthorities(privileges: List<String>): List<GrantedAuthority> {
        val authorities = privileges.map { SimpleGrantedAuthority(it) }

        return authorities
    }
}

package michalr.tasks.config

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl

@Configuration
class RoleHierarchyConfig {

    private val log = KotlinLogging.logger {}
    private val adminRole = "ADMIN"
    private val userRole = "USER"

    @Bean
    fun roleHierarchy(): RoleHierarchy {
        return  RoleHierarchyImpl.withDefaultRolePrefix()
            .role(adminRole).implies(userRole)
            .build()
    }
}

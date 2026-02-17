package michalr.tasks.config

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
class WebSecurityConfig {

    private val log = KotlinLogging.logger {}

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
            .headers { headers ->
                headers.frameOptions { it.disable() } // required for H2 console
            }
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/h2-console/**").permitAll()
                    .requestMatchers("/register").permitAll()
                    .requestMatchers("/login").permitAll()
                    .requestMatchers(HttpMethod.GET, "/task").hasRole("USER")
                    .anyRequest().authenticated()
            }

        return http.build()
    }

    @Bean
    fun authenticationManager(
        authenticationConfiguration: AuthenticationConfiguration
    ): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }
}

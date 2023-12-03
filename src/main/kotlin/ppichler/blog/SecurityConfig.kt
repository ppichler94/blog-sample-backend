package ppichler.blog

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            cors { disable() }
            csrf { disable() }
            authorizeRequests {
                authorize(anyRequest, permitAll)
            }
            formLogin {
            }
            exceptionHandling {
                authenticationEntryPoint = Http403ForbiddenEntryPoint()
            }
        }
        return http.build()
    }
}
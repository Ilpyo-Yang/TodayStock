package dev.todaystock.api.common.config

import dev.todaystock.api.common.security.AuthenticationFilter
import dev.todaystock.api.common.security.CustomAuthenticationEntryPoint
import dev.todaystock.api.common.security.TokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val tokenProvider: TokenProvider,
    private val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint
){
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .httpBasic { it.disable() }
            .csrf { it.disable() }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests {
                it.requestMatchers("/v1/collect/**", "/v1/search", "/v1/member/delete").authenticated()
                    .anyRequest().permitAll()
            }
            .addFilterAfter(
                AuthenticationFilter(tokenProvider),
                BasicAuthenticationFilter::class.java
            )
            .httpBasic {
                it.authenticationEntryPoint(customAuthenticationEntryPoint)
            }

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}
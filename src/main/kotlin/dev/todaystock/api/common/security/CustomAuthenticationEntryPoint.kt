package dev.todaystock.api.common.security

import com.fasterxml.jackson.databind.ObjectMapper
import dev.todaystock.api.common.exception.ErrorCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        authException: AuthenticationException?
    ) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.characterEncoding = "utf-8"
        response.contentType = "application/json"
        val result = ObjectMapper().writeValueAsString(ErrorCode.InValidToken.message)
        response.writer.write(result)
    }
}
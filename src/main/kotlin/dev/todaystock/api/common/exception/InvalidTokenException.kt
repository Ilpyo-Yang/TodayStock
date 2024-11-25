package dev.todaystock.api.common.exception

import org.springframework.security.core.AuthenticationException

class InvalidTokenException(
    override val message: String = "",
    val fieldName: String = "Invalid Token"
) : AuthenticationException(message)
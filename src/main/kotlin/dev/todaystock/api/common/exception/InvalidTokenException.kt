package dev.todaystock.api.common.exception

class InvalidTokenException(
    override val message: String = "",
    val fieldName: String = "Invalid Token"
) : RuntimeException(message)
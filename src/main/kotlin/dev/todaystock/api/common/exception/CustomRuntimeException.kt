package dev.todaystock.api.common.exception

class CustomRuntimeException(
    override val message: String = "",
    val fieldName: String = "Runtime Error",
) : RuntimeException(message)
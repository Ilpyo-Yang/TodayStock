package dev.todaystock.api.common.exception

class CustomRuntimeException(
    val fieldName: String = "",
    message: String = "Runtime Error"
) : RuntimeException(message)
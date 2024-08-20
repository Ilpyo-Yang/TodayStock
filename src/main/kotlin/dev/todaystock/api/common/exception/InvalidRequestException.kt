package dev.todaystock.api.common.exception

class InvalidRequestException(
    val fieldName: String = "",
    message: String = "Invalid Input"
) : RuntimeException(message)
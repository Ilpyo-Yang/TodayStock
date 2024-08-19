package dev.todaystock.api.common.exception

class InValidRequestException(
    val fieldName: String = "",
    message: String = "Invalid Input"
) : RuntimeException(message)
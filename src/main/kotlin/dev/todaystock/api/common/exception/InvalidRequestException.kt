package dev.todaystock.api.common.exception

class InvalidRequestException(
    override val message: String = "",
    val fieldName: String = "Invalid Input"
) : RuntimeException(message)
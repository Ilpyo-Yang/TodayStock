package dev.todaystock.api.common.exception

class NoSuchElementException(
    override val message: String = "",
    val fieldName: String = "Element Not Found"
) : RuntimeException(message)
package dev.todaystock.api.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: Int,
    val message: String,
    val httpStatus: HttpStatus,
) {
    CollectNotFound(200, "Collection이 없습니다.", HttpStatus.NOT_FOUND)
}
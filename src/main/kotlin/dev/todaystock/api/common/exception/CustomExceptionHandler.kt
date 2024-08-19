package dev.todaystock.api.common.exception

import dev.todaystock.api.common.dto.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CustomExceptionHandler {
    @ExceptionHandler(InValidRequestException::class)
    protected fun inValidRequestException(ex: InValidRequestException): ResponseEntity<ApiResponse<Map<String, String>>> {
        val errors = mapOf(ex.fieldName to (ex.message ?: "Not Exception Message"))
        return ResponseEntity(ApiResponse(
            ErrorCode.InValidRequest.name,
            ErrorCode.InValidRequest.message,
            errors
        ), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    protected fun defaultException(ex: Exception): ResponseEntity<ApiResponse<Map<String, String>>> {
        val errors = mapOf(" " to (ex.message ?: "Not Exception Message"))
        return ResponseEntity(ApiResponse(
            ErrorCode.DefaultError.name,
            ErrorCode.DefaultError.message,
            errors
        ), HttpStatus.BAD_REQUEST)
    }
}
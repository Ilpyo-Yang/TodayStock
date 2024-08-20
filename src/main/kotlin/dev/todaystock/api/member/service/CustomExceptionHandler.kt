package dev.todaystock.api.member.service

import dev.todaystock.api.common.dto.ApiResponse
import dev.todaystock.api.common.exception.ErrorCode
import dev.todaystock.api.common.exception.InvalidRequestException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CustomExceptionHandler {
    @ExceptionHandler(InvalidRequestException::class)
    protected fun invalidRequestException(ex: InvalidRequestException): ResponseEntity<ApiResponse<Map<String, String>>> {
        val errors = mapOf(ex.fieldName to (ex.message ?: "Not Exception Message"))
        return ResponseEntity(ApiResponse(
            ErrorCode.InValidRequest.name,
            ErrorCode.InValidRequest.message,
            errors
        ), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(BadCredentialsException::class)
    protected fun badCredentialsException(ex: BadCredentialsException): ResponseEntity<ApiResponse<Map<String, String>>> {
        val errors = mapOf("Signin failed" to "Check your email and password")
        return ResponseEntity(ApiResponse(
            ErrorCode.BadCredentials.name,
            ErrorCode.BadCredentials.message,
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
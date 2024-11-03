package dev.todaystock.api.common.exception

import dev.todaystock.api.common.dto.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CustomExceptionHandler {
//    @ExceptionHandler(MethodArgumentNotValidException::class)
//    protected fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<ApiResponse<Map<String, String>>> {
//        val errors = mutableMapOf<String, String>()
//        ex.bindingResult.allErrors.forEach { error ->
//            val fieldName = (error as FieldError).field
//            val errorMessage = error.getDefaultMessage()
//            errors[fieldName] = errorMessage ?: "Not Exception Message"
//        }
//        return ResponseEntity(ApiResponse(
//            ErrorCode.DefaultError.name,
//            ErrorCode.DefaultError.message,
//            errors
//        ), HttpStatus.BAD_REQUEST)
//    }

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

    @ExceptionHandler(CustomRuntimeException::class)
    protected fun customRuntimeException(ex: CustomRuntimeException): ResponseEntity<ApiResponse<Map<String, String>>> {
        val errors = mapOf("RuntimeError" to "Service not available: ${ex.message}")
        return ResponseEntity(ApiResponse(
            ErrorCode.ServerError.name,
            ErrorCode.ServerError.message,
            errors
        ), HttpStatus.BAD_REQUEST)
    }

//    @ExceptionHandler(Exception::class)
//    protected fun defaultException(ex: Exception): ResponseEntity<ApiResponse<Map<String, String>>> {
//        val errors = mapOf(" " to (ex.message ?: "Not Exception Message"))
//        return ResponseEntity(ApiResponse(
//            ErrorCode.DefaultError.name,
//            ErrorCode.DefaultError.message,
//            errors
//        ), HttpStatus.BAD_REQUEST)
//    }
}
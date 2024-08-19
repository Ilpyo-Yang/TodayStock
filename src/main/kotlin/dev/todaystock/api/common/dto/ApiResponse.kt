package dev.todaystock.api.common.dto

import dev.todaystock.api.common.exception.ErrorCode
import org.springframework.http.HttpStatus

class ApiResponse (
    private val status: String,
    private val message: String?,
    private val data: Any?
) {
    companion object {
        fun successResponse(): ApiResponse = ApiResponse(
            status = HttpStatus.OK.name,
            message = HttpStatus.OK.reasonPhrase,
            data = null
        )

        fun successDataResponse(any: Any?): ApiResponse = ApiResponse(
            status = HttpStatus.OK.name,
            message = HttpStatus.OK.reasonPhrase,
            data = any
        )

        fun failedResponse(errorCode: ErrorCode): ApiResponse = ApiResponse(
            status = errorCode.code,
            message = errorCode.message,
            data = errorCode.httpStatus
        )
    }
}

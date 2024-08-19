package dev.todaystock.api.common.dto

import dev.todaystock.api.common.exception.ErrorCode
import org.springframework.http.HttpStatus

class ApiResponse<T> (
    private val status: String,
    private val message: String?,
    private val data: T? = null
) {
    companion object {
        fun successResponse(): ApiResponse<Nothing> = ApiResponse(
            status = HttpStatus.OK.name,
            message = HttpStatus.OK.reasonPhrase,
            data = null
        )

        fun <T> successDataResponse(data: T?): ApiResponse<T> = ApiResponse(
            status = HttpStatus.OK.name,
            message = HttpStatus.OK.reasonPhrase,
            data = data
        )

        fun failedResponse(errorCode: ErrorCode): ApiResponse<HttpStatus> = ApiResponse(
            status = errorCode.code,
            message = errorCode.message,
            data = errorCode.httpStatus
        )
    }
}

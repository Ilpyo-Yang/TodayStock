package dev.todaystock.api.common.dto

import dev.todaystock.api.common.exception.ErrorCode
import org.springframework.http.HttpStatus

class ApiResponse<T> (
    val status: String,
    val message: String?,
    val data: Any?
) {
    companion object {
        fun successResponse(): ApiResponse<Nothing> = ApiResponse(
            status = HttpStatus.OK.name,
            message = "Success",
            data = null
        )

        fun <T> successDataResponse(data: T?): ApiResponse<T> = ApiResponse(
            status = HttpStatus.OK.name,
            message = "Success",
            data = data
        )

        fun failedResponse(errorCode: ErrorCode): ApiResponse<HttpStatus> = ApiResponse(
            status = errorCode.code,
            message = errorCode.message,
            data = errorCode.httpStatus
        )
    }
}

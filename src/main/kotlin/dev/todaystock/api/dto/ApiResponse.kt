package dev.todaystock.api.dto

import dev.todaystock.api.exception.ErrorCode
import org.springframework.http.HttpStatus

class ApiResponse (
    var status: String,
    var message: String?,
    var data: Any?
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

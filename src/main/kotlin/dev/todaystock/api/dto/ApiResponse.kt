package dev.todaystock.api.dto

import dev.todaystock.api.exception.ErrorCode
import org.springframework.http.HttpStatus

class ApiResponse (
    var status: Int,
    var message: String?,
    var data: Any?
) {
    companion object {
        fun successResponse(): ApiResponse = ApiResponse(
            status = HttpStatus.OK.value(),
            message = HttpStatus.OK.reasonPhrase,
            data = null
        )

        fun successDataResponse(any: Any?): ApiResponse = ApiResponse(
            status = HttpStatus.OK.value(),
            message = HttpStatus.OK.reasonPhrase,
            data = any
        )

        fun failedResponse(errorCode: ErrorCode): ApiResponse = ApiResponse(
            status = errorCode.status,
            message = errorCode.message,
            data = errorCode.httpStatus
        )
    }
}

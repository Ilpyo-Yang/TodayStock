package dev.todaystock.api.common.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val code: String,
    val message: String,
    val httpStatus: HttpStatus,
) {
    DefaultError("E401", "미처리 에러 내역입니다.", HttpStatus.BAD_REQUEST),
    NotFound("E404", "조회한 내용이 없습니다.", HttpStatus.NOT_FOUND),
    NotDeleted("E409", "삭제되지 않았습니다.", HttpStatus.CONFLICT),
    InValidRequest("E410", "올바르지 않은 요청값입니다. ", HttpStatus.BAD_REQUEST);

}
package dev.todaystock.api.common.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val code: String,
    val message: String,
    val httpStatus: HttpStatus,
) {
    // common
    DefaultError("E4010", "에러가 발생했습니다.", HttpStatus.BAD_REQUEST),
    NotFound("E4040", "조회한 내용이 없습니다.", HttpStatus.NOT_FOUND),
    NotDeleted("E4090", "삭제되지 않았습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    // security
    InValidRequest("E4200", "올바르지 않은 요청값입니다. ", HttpStatus.BAD_REQUEST),
    BadCredentials("E4210", "아이디 혹은 비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST)

}
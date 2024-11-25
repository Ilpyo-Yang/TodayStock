package dev.todaystock.api.info.controller

import dev.todaystock.api.common.dto.ApiResponse
import dev.todaystock.api.common.exception.ErrorCode
import dev.todaystock.api.info.dto.InfoTypeRequest
import dev.todaystock.api.info.dto.InfoTypeResponse
import dev.todaystock.api.info.dto.UuidDto
import dev.todaystock.api.info.entity.InfoType
import dev.todaystock.api.info.service.InfoTypeService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/info/{infoType}")
@Tag(name = "InfoType API", description = "검색한 정보유형과 관련된 API입니다. Type 종류는 Company, Country, Theme 세 종류입니다.")
class InfoTypeController(
    private val infoTypeService: InfoTypeService
) {
    @GetMapping
    @Operation(summary = "정보 유형 검색", description = "정보 유형 검색 API")
    fun findByInfoType(@PathVariable infoType: InfoType): ResponseEntity<ApiResponse<List<InfoTypeResponse?>>> {
        return ResponseEntity.ok(ApiResponse.successDataResponse(infoTypeService.findAll(infoType)))
    }

    @PostMapping
    @Operation(summary = "정보 유형 생성", description = "정보 유형 생성 API")
    fun create(@PathVariable infoType: InfoType,
               @RequestBody request: InfoTypeRequest
    ): ResponseEntity<ApiResponse<InfoTypeResponse?>> {
        return ResponseEntity.ok(ApiResponse.successDataResponse(infoTypeService.create(infoType, request)))
    }

    @DeleteMapping
    @Operation(summary = "정보 유형 삭제", description = "정보 유형 삭제 API")
    fun delete(@PathVariable infoType: InfoType,
               @RequestBody(required = true) uuidDto: UuidDto
    ): ResponseEntity<ApiResponse<out HttpStatus>> {
        if(infoTypeService.delete(infoType, uuidDto.uuid)) {
            return ResponseEntity.ok(ApiResponse.successResponse())
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failedResponse(ErrorCode.NotDeleted))
    }
}
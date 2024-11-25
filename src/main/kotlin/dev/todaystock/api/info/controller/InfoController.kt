package dev.todaystock.api.info.controller

import dev.todaystock.api.common.dto.ApiResponse
import dev.todaystock.api.common.exception.ErrorCode
import dev.todaystock.api.info.dto.InfoResponse
import dev.todaystock.api.info.dto.UuidDto
import dev.todaystock.api.info.entity.InfoType
import dev.todaystock.api.info.service.InfoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/info/{infoType}/detail")
@Tag(name = "Info API", description = "검색한 정보와 관련된 API입니다.")
class InfoController(
    private val infoService: InfoService
) {
    @GetMapping
    @Operation(summary = "정보 검색", description = "정보 검색 API")
    fun findByInfoTypeUuid(@PathVariable infoType: InfoType,
                           @RequestBody(required = true) uuidDto: UuidDto
    ): ResponseEntity<ApiResponse<List<InfoResponse?>>> {
        return ResponseEntity.ok(ApiResponse.successDataResponse(infoService.findByInfoTypeUuid(uuidDto.uuid, infoType)))
    }

    @DeleteMapping
    @Operation(summary = "정보 삭제", description = "정보 삭제 API")
    fun delete(@PathVariable infoType: InfoType,
               @RequestBody(required = true) uuidDto: UuidDto
    ): ResponseEntity<ApiResponse<out HttpStatus>> {
        if(infoService.delete(infoType, uuidDto.uuid)) {
            return ResponseEntity.ok(ApiResponse.successResponse())
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failedResponse(ErrorCode.NotDeleted))
    }
}
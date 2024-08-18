package dev.todaystock.api.controller

import dev.todaystock.api.dto.ApiResponse
import dev.todaystock.api.dto.info.InfoRequest
import dev.todaystock.api.dto.info.InfoTypeRequest
import dev.todaystock.api.entity.type.InfoType
import dev.todaystock.api.exception.ErrorCode
import dev.todaystock.api.service.InfoService
import dev.todaystock.api.service.InfoTypeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/info/{infoType}")
class InfoTypeController(
    private val infoTypeService: InfoTypeService
) {
    @GetMapping
    fun findByInfoType(@PathVariable infoType: InfoType): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok().body(ApiResponse.successDataResponse(infoTypeService.findAll(infoType)))
    }

    @PostMapping
    fun create(@PathVariable infoType: InfoType,
               @RequestBody request: InfoTypeRequest
    ): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok().body(ApiResponse.successDataResponse(infoTypeService.create(infoType, request)))
    }

    // todo user check needed
    @DeleteMapping
    fun delete(@PathVariable infoType: InfoType,
               @RequestBody(required = true) uuid: UUID): ResponseEntity<ApiResponse> {
        if(infoTypeService.delete(infoType, uuid)) {
            return ResponseEntity.ok(ApiResponse.successResponse())
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failedResponse(ErrorCode.NotDeleted))
    }
}
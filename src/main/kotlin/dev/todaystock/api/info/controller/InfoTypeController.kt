package dev.todaystock.api.info.controller

import dev.todaystock.api.collect.dto.CollectResponse
import dev.todaystock.api.common.dto.ApiResponse
import dev.todaystock.api.info.dto.InfoTypeRequest
import dev.todaystock.api.info.entity.InfoType
import dev.todaystock.api.common.exception.ErrorCode
import dev.todaystock.api.info.dto.InfoTypeResponse
import dev.todaystock.api.info.service.InfoTypeService
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
    fun findByInfoType(@PathVariable infoType: InfoType): ResponseEntity<ApiResponse<List<InfoTypeResponse?>>> {
        return ResponseEntity.ok().body(ApiResponse.successDataResponse(infoTypeService.findAll(infoType)))
    }

    @PostMapping
    fun create(@PathVariable infoType: InfoType,
               @RequestBody request: InfoTypeRequest
    ): ResponseEntity<ApiResponse<InfoTypeResponse?>> {
        return ResponseEntity.ok().body(ApiResponse.successDataResponse(infoTypeService.create(infoType, request)))
    }

    @DeleteMapping
    fun delete(@PathVariable infoType: InfoType,
               @RequestBody(required = true) uuid: UUID): ResponseEntity<ApiResponse<out HttpStatus>> {
        if(infoTypeService.delete(infoType, uuid)) {
            return ResponseEntity.ok(ApiResponse.successResponse())
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failedResponse(ErrorCode.NotDeleted))
    }
}
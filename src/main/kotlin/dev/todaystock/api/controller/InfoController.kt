package dev.todaystock.api.controller

import dev.todaystock.api.dto.ApiResponse
import dev.todaystock.api.dto.info.InfoRequest
import dev.todaystock.api.entity.type.InfoType
import dev.todaystock.api.exception.ErrorCode
import dev.todaystock.api.service.InfoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/info/{infoType}/detail")
class InfoController(
    private val infoService: InfoService
) {
    @GetMapping
    fun findByInfoUuid(@PathVariable infoType: InfoType,
                       @RequestBody(required = true) infoUuid: UUID
    ): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok().body(ApiResponse.successDataResponse(infoService.findAll(infoUuid, infoType)))
    }

    @PostMapping
    fun create(@PathVariable infoType: InfoType,
               @RequestBody request: InfoRequest
    ): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok().body(ApiResponse.successDataResponse(infoService.create(infoType, request)))
    }

    // todo user check needed
    @DeleteMapping
    fun delete(@PathVariable infoType: InfoType,
               @RequestBody(required = true) uuid: UUID): ResponseEntity<ApiResponse> {
        if(infoService.delete(infoType, uuid)) {
            return ResponseEntity.ok(ApiResponse.successResponse())
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failedResponse(ErrorCode.NotDeleted))
    }
}
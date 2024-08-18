package dev.todaystock.api.controller

import dev.todaystock.api.dto.ApiResponse
import dev.todaystock.api.dto.collect.CollectRequest
import dev.todaystock.api.entity.type.InfoType
import dev.todaystock.api.exception.ErrorCode
import dev.todaystock.api.service.CollectService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/collect")
class CollectController(
    private val collectService: CollectService
) {
    @GetMapping
    fun findByUserUuid(@RequestParam(required = true) userUuid: UUID): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(ApiResponse.successDataResponse(collectService.findByUserUuid(userUuid)))
    }

    @GetMapping("/type")
    fun findByUserUuidAndType(@RequestParam(required = true) userUuid: UUID,
                              @RequestParam(required = true) infoType: InfoType
    ): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(ApiResponse.successDataResponse(collectService.findByUserUuidAndType(userUuid, infoType)))
    }

    @PostMapping
    fun create(@RequestBody request: CollectRequest): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(ApiResponse.successDataResponse(collectService.create(request)))
    }

    // todo user check needed
    @DeleteMapping
    fun delete(@RequestBody(required = true) uuid: UUID): ResponseEntity<ApiResponse> {
        if(collectService.delete(uuid)) {
            return ResponseEntity.ok(ApiResponse.successResponse())
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failedResponse(ErrorCode.NotDeleted))
    }
}
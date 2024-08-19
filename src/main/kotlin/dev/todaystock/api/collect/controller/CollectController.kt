package dev.todaystock.api.collect.controller

import dev.todaystock.api.collect.service.CollectService
import dev.todaystock.api.common.dto.ApiResponse
import dev.todaystock.api.collect.dto.CollectRequest
import dev.todaystock.api.collect.dto.CollectResponse
import dev.todaystock.api.collect.entity.Collect
import dev.todaystock.api.info.entity.InfoType
import dev.todaystock.api.common.exception.ErrorCode
import jakarta.validation.Valid
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
    fun findByUserUuid(@RequestParam(required = true) userUuid: UUID): ResponseEntity<ApiResponse<List<String?>>> {
        return ResponseEntity.ok(ApiResponse.successDataResponse(collectService.findByUserUuid(userUuid)))
    }

    @GetMapping("/type")
    fun findByUserUuidAndType(@RequestParam(required = true) userUuid: UUID,
                              @RequestParam(required = true) infoType: InfoType
    ): ResponseEntity<ApiResponse<List<Collect?>>> {
        return ResponseEntity.ok(ApiResponse.successDataResponse(collectService.findByUserUuidAndType(userUuid, infoType)))
    }

    @PostMapping
    fun create(@RequestBody @Valid request: CollectRequest): ResponseEntity<ApiResponse<CollectResponse?>> {
        return ResponseEntity.ok(ApiResponse.successDataResponse(collectService.create(request)))
    }

    // todo user check needed
    @DeleteMapping
    fun delete(@RequestBody(required = true) uuid: UUID): ResponseEntity<ApiResponse<out HttpStatus>> {
        if(collectService.delete(uuid)) {
            return ResponseEntity.ok(ApiResponse.successResponse())
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failedResponse(ErrorCode.NotDeleted))
    }
}
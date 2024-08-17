package dev.todaystock.api.controller

import dev.todaystock.api.dto.ApiResponse
import dev.todaystock.api.dto.collect.CollectRequest
import dev.todaystock.api.entity.InfoType
import dev.todaystock.api.service.CollectService
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
        return ResponseEntity.ok().body(ApiResponse.successDataResponse(collectService.findByUserUuid(userUuid)))
    }

    @GetMapping("/type")
    fun findByUserUuidAndType(@RequestParam(required = true) userUuid: UUID,
                              @RequestParam(required = true) infoType: InfoType
    ): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok().body(ApiResponse.successDataResponse(collectService.findByUserUuidAndType(userUuid, infoType)))
    }

    @PostMapping
    fun create(@RequestBody request: CollectRequest): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok().body(ApiResponse.successDataResponse(collectService.create(request)))
    }

    @DeleteMapping
    fun delete(@RequestParam(required = true) uuid: UUID): ResponseEntity<Any> = ResponseEntity.ok().body(collectService.delete(uuid))

}
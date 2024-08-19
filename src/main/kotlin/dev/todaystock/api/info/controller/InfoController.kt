package dev.todaystock.api.info.controller

import dev.todaystock.api.common.dto.ApiResponse
import dev.todaystock.api.info.dto.InfoRequest
import dev.todaystock.api.common.exception.ErrorCode
import dev.todaystock.api.info.dto.InfoResponse
import dev.todaystock.api.info.entity.InfoType
import dev.todaystock.api.info.service.InfoService
import jakarta.validation.Valid
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
    ): ResponseEntity<ApiResponse<List<InfoResponse?>>> {
        return ResponseEntity.ok().body(ApiResponse.successDataResponse(infoService.findAll(infoUuid, infoType)))
    }

    @PostMapping
    fun create(@PathVariable infoType: InfoType,
               @RequestBody @Valid request: InfoRequest
    ): ResponseEntity<ApiResponse<InfoResponse?>> {
        return ResponseEntity.ok().body(ApiResponse.successDataResponse(infoService.create(infoType, request)))
    }

    // todo user check needed
    @DeleteMapping
    fun delete(@PathVariable infoType: InfoType,
               @RequestBody(required = true) uuid: UUID
    ): ResponseEntity<ApiResponse<out HttpStatus>> {
        if(infoService.delete(infoType, uuid)) {
            return ResponseEntity.ok(ApiResponse.successResponse())
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failedResponse(ErrorCode.NotDeleted))
    }
}
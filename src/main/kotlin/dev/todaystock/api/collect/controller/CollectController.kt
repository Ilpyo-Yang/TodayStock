package dev.todaystock.api.collect.controller

import dev.todaystock.api.collect.service.CollectService
import dev.todaystock.api.common.dto.ApiResponse
import dev.todaystock.api.collect.dto.CollectRequest
import dev.todaystock.api.collect.dto.CollectResponse
import dev.todaystock.api.collect.entity.Collect
import dev.todaystock.api.info.entity.InfoType
import dev.todaystock.api.common.exception.ErrorCode
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/collect")
@Tag(name = "Collect API", description = "정보 수집한 내용으로 만든 Collect 설정 API입니다.")
class CollectController(
    private val collectService: CollectService
) {
    @GetMapping
    @Operation(summary = "특정 사용자의 Collect 정보유형 종류 검색", description = "Collect 수집한 정보 유형 검색 API")
    fun findByMemberUuid(@AuthenticationPrincipal loginUser: User,
                         @RequestParam(required = true) memberUuid: UUID
    ): ResponseEntity<ApiResponse<List<String?>>> {
        return ResponseEntity.ok(ApiResponse.successDataResponse(collectService.findByMemberUuid(memberUuid)))
    }

    @GetMapping("/type")
    @Operation(summary = "특정 사용자의 특정 유형의 Collect 검색", description = "Collect 수집한 특정 정보 유형 검색 API")
    fun findByMemberUuidAndType(@AuthenticationPrincipal loginUser: User,
                                @RequestParam(required = true) memberUuid: UUID,
                                @RequestParam(required = true) infoType: InfoType
    ): ResponseEntity<ApiResponse<List<Collect?>>> {
        return ResponseEntity.ok(ApiResponse.successDataResponse(collectService.findByMemberUuidAndType(memberUuid, infoType)))
    }

    @PostMapping
    @Operation(summary = "정보 유형 생성", description = "정보 유형 생성 API")
    fun create(@RequestBody @Valid request: CollectRequest
    ): ResponseEntity<ApiResponse<CollectResponse?>> {
        return ResponseEntity.ok(ApiResponse.successDataResponse(collectService.create(request)))
    }

    @DeleteMapping
    @Operation(summary = "정보 유형 삭제", description = "정보 유형 삭제 API")
    fun delete(@AuthenticationPrincipal loginUser: User,
               @RequestParam(required = true) uuid: UUID
    ): ResponseEntity<ApiResponse<out HttpStatus>> {
        if(collectService.delete(uuid)) {
            return ResponseEntity.ok(ApiResponse.successResponse())
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failedResponse(ErrorCode.NotDeleted))
    }
}
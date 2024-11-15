package dev.todaystock.api.chat.controller

import dev.todaystock.api.chat.service.SearchService
import dev.todaystock.api.common.dto.ApiResponse
import dev.todaystock.api.info.entity.InfoType
import dev.todaystock.api.info.entity.MarkerInfo
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/search")
@Tag(name = "Search API", description = "정보 검색을 위한 API입니다.")
class SearchController (
    private val searchService: SearchService
) {
    @GetMapping
    @Operation(summary = "최신 뉴스 검색", description = "최신 뉴스 검색을 바탕으로 AI로 정보를 추가해서 정보를 저장하는 API")
    fun searchInfo(@RequestParam(required = true) keyword: String,
                   @RequestParam(required = true) infoType: InfoType
    ): ResponseEntity<ApiResponse<List<MarkerInfo>>> {
        return ResponseEntity.ok(ApiResponse.successDataResponse(searchService.searchKeyword(keyword, infoType)))
    }
}
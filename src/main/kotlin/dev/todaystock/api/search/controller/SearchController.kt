package dev.todaystock.api.chat.controller

import dev.todaystock.api.chat.dto.SearchRequest
import dev.todaystock.api.chat.service.SearchService
import dev.todaystock.api.common.dto.ApiResponse
import dev.todaystock.api.info.entity.MarkerInfo
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/search")
class SearchController (
    private val searchService: SearchService
) {
    @GetMapping
    fun searchInfo(@RequestBody @Valid searchRequest: SearchRequest
    ): ResponseEntity<ApiResponse<List<MarkerInfo>>> {
        return ResponseEntity.ok(ApiResponse.successDataResponse(searchService.searchKeyword(searchRequest)))
    }
}
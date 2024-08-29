package dev.todaystock.api.chat.controller

import dev.todaystock.api.chat.service.SearchService
import dev.todaystock.api.common.dto.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/search")
class ChatController (
    private val searchService: SearchService
) {
    @GetMapping
    fun searchInfo(@RequestParam(required = true) keyword: String
    ): ResponseEntity<ApiResponse<ResponseEntity<String>>> {
        return ResponseEntity.ok(ApiResponse.successDataResponse(searchService.searchNews(keyword)))
    }
}
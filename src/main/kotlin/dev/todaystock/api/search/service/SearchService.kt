package dev.todaystock.api.chat.service

import dev.todaystock.api.chat.dto.NewsItemList
import dev.todaystock.api.common.exception.CustomRuntimeException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.toEntity
import org.springframework.web.util.UriComponentsBuilder


@Service
class SearchService {
    @Value("\${naver.api.client.id}")
    lateinit var clientId: String

    @Value("\${naver.api.client.secret}")
    lateinit var clientSecret: String

    fun searchNews(keyword: String): ResponseEntity<NewsItemList> {
        val customClient = RestClient.builder()
            .baseUrl("https://openapi.naver.com/")
            .defaultHeader("X-Naver-Client-Id", clientId)
            .defaultHeader("X-Naver-Client-Secret", clientSecret)
            .build()

        val uri = UriComponentsBuilder
            .fromUriString("https://openapi.naver.com/v1/search/news.json")
            .build("query", keyword)

        val result = customClient.get()
            .uri(uri)
            .accept(APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) { _, response ->
                throw CustomRuntimeException("Searching news failed, " + response.statusText) }
            .toEntity<NewsItemList>()

        return result
    }

//    fun searchNews(keyword: String): ResponseEntity<String> {
//
//    }
}

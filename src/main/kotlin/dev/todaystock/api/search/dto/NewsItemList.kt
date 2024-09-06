package dev.todaystock.api.chat.dto

import com.fasterxml.jackson.annotation.JsonProperty
import dev.todaystock.api.search.dto.NewsItem

class NewsItemList(
    @JsonProperty("lastBuildDate")
    private val lastBuildDate: String,

    @JsonProperty("total")
    private val total: Int,

    @JsonProperty("start")
    private val start: Int,

    @JsonProperty("display")
    private val display: Int,

    @JsonProperty("items")
    private val _items: List<NewsItem>
) {
    val items: List<NewsItem>
        get() = _items
}

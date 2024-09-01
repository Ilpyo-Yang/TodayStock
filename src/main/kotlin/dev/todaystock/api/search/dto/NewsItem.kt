package dev.todaystock.api.search.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

class NewsItem (
    @JsonProperty("title")
    private val _title: String,

    @JsonProperty("originallink")
    private val originallink: String,

    @JsonProperty("link")
    private val _link: String,

    @JsonProperty("description")
    private val _description: String,

    @JsonProperty("pubDate")
    private val _pubDate: LocalDateTime
) {
    val title: String
        get() = _title

    val link: String
        get() = _link

    val description: String
        get() = _description

    val pubDate: LocalDateTime
        get() = _pubDate


}
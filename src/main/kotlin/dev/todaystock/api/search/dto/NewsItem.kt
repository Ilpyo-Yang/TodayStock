package dev.todaystock.api.search.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

class NewsItem (
    @JsonProperty("title")
    private val title: String,

    @JsonProperty("originallink")
    private val originallink: String,

    @JsonProperty("link")
    private val link: String,

    @JsonProperty("description")
    private val description: String,

    @JsonProperty("pubDate")
    private val pubDate: LocalDateTime
)
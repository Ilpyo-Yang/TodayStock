package dev.todaystock.api.search.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

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
    private val _pubDate: String
) {
    val title: String
        get() = _title

    val link: String
        get() = _link

    val description: String
        get() = _description

    val pubDate: LocalDateTime
        get() = ZonedDateTime.parse(_pubDate, DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z"))
            .withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()


}
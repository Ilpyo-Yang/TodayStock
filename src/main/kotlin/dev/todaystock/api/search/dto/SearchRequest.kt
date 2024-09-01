package dev.todaystock.api.chat.dto

import com.fasterxml.jackson.annotation.JsonProperty
import dev.todaystock.api.info.entity.InfoType
import jakarta.validation.constraints.NotBlank

class SearchRequest(
    @JsonProperty("keyword")
    @field:NotBlank(message = "Keyword cannot be empty")
    private val _keyword: String,

    @JsonProperty("infoType")
    @field:NotBlank(message = "InfoType cannot be empty")
    private val _infoType: String,
) {
    val keyword: String
        get() = _keyword

    val infoType: InfoType
        get() = InfoType.valueOf(_infoType)
}
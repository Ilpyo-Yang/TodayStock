package dev.todaystock.api.search.dto

import kotlinx.serialization.Serializable

@Serializable
data class SearchInfoType(
    val description: String,
    val tickerCode: String
)

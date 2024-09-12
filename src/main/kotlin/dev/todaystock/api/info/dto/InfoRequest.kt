package dev.todaystock.api.info.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import dev.todaystock.api.info.entity.*
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class InfoRequest(
    @JsonProperty("uuid")
    private val _uuid: String?,

    @JsonProperty("typeUuid")
    @field:NotBlank(message = "TypeUuid cannot be empty")
    private val _typeUuid: String,

    @JsonProperty("title")
    @field:NotBlank(message = "Title cannot be empty")
    private val _title: String,

    @JsonProperty("link")
    @field:NotBlank(message = "Link cannot be empty")
    private val _link: String,

    @JsonProperty("info")
    @field:NotBlank(message = "Info cannot be empty")
    private val _info: String,

    @JsonProperty("pubDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @field:NotBlank(message = "PubDate cannot be empty")
    private val _pubDate: String
) {
    val uuid: UUID
        get() = UUID.fromString(_uuid)
    val typeUuid: UUID
        get() = UUID.fromString(_typeUuid)
    val title: String
        get() = _title
    val link: String
        get() = _link
    val info: String
        get() = _info
    val pubDate: LocalDateTime
        get() = LocalDateTime.parse(_pubDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

    companion object {
        fun toCompanyInfo(infoRequest: InfoRequest, company: Company): CompanyInfo = CompanyInfo(
            uuid = infoRequest.uuid,
            company = company,
            title = infoRequest.title,
            link = infoRequest.link,
            info = infoRequest.info,
            publishedDttm = infoRequest.pubDate
        )

        fun toCountryInfo(infoRequest: InfoRequest, country: Country): CountryInfo = CountryInfo(
            uuid = infoRequest.uuid,
            country = country,
            title = infoRequest.title,
            link = infoRequest.link,
            info = infoRequest.info,
            publishedDttm = infoRequest.pubDate
        )

        fun toThemeInfo(infoRequest: InfoRequest, theme: Theme): ThemeInfo = ThemeInfo(
            uuid = infoRequest.uuid,
            theme = theme,
            title = infoRequest.title,
            link = infoRequest.link,
            info = infoRequest.info,
            publishedDttm = infoRequest.pubDate
        )
    }
}

package dev.todaystock.api.info.dto

import com.fasterxml.jackson.annotation.JsonProperty
import dev.todaystock.api.info.entity.CompanyInfo
import dev.todaystock.api.info.entity.CountryInfo
import dev.todaystock.api.info.entity.InfoType
import dev.todaystock.api.info.entity.ThemeInfo
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.validation.constraints.NotBlank
import java.util.*

class InfoRequest(
    @JsonProperty("uuid")
    private val _uuid: String?,

    @JsonProperty("typeUuid")
    @field:NotBlank(message = "TypeUuid cannot be empty")
    private val _typeUuid: String,

    @JsonProperty("summary")
    @field:NotBlank(message = "Summary cannot be empty")
    private val _summary: String,

    @JsonProperty("info")
    @field:NotBlank(message = "Info cannot be empty")
    private val _info: String
) {
    val uuid: UUID
        get() = UUID.fromString(_uuid)
    val typeUuid: UUID
        get() = UUID.fromString(_typeUuid)
    val summary: String
        get() = _summary
    val info: String
        get() = _info

    companion object {
        fun toCompanyInfo(infoRequest: InfoRequest): CompanyInfo = CompanyInfo(
            uuid = infoRequest.uuid,
            companyUuid = infoRequest.typeUuid,
            summary = infoRequest.summary,
            info = infoRequest.info,
        )

        fun toCountryInfo(infoRequest: InfoRequest): CountryInfo = CountryInfo(
            uuid = infoRequest.uuid,
            countryUuid = infoRequest.typeUuid,
            summary = infoRequest.summary,
            info = infoRequest.info,
        )

        fun toThemeInfo(infoRequest: InfoRequest): ThemeInfo = ThemeInfo(
            uuid = infoRequest.uuid,
            themeUuid = infoRequest.typeUuid,
            summary = infoRequest.summary,
            info = infoRequest.info,
        )
    }
}

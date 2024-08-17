package dev.todaystock.api.dto.info

import dev.todaystock.api.entity.*
import java.util.*

class InfoRequest(
    var uuid: UUID?,
    var typeUuid: UUID,
    var summary: String,
    var description: String
) {
    companion object {
        fun toCompanyInfo(infoRequest: InfoRequest): CompanyInfo = CompanyInfo(
            uuid = infoRequest.uuid,
            companyUuid = infoRequest.typeUuid,
            summary = infoRequest.summary,
            description = infoRequest.description,
        )

        fun toCountryInfo(infoRequest: InfoRequest): CountryInfo = CountryInfo(
            uuid = infoRequest.uuid,
            countryUuid = infoRequest.typeUuid,
            summary = infoRequest.summary,
            description = infoRequest.description,
        )

        fun toThemeInfo(infoRequest: InfoRequest): ThemeInfo = ThemeInfo(
            uuid = infoRequest.uuid,
            themeUuid = infoRequest.typeUuid,
            summary = infoRequest.summary,
            description = infoRequest.description,
        )
    }
}

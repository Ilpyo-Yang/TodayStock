package dev.todaystock.api.dto.info

import dev.todaystock.api.entity.*
import java.util.*

class InfoResponse(
    var uuid: UUID?,
    var typeUuid: UUID,
    var summary: String,
    var description: String
) {
    companion object {
        fun fromCompanyInfo(companyInfo: CompanyInfo): InfoResponse = InfoResponse(
            uuid = companyInfo.uuid,
            typeUuid = companyInfo.companyUuid,
            summary = companyInfo.summary,
            description = companyInfo.description
        )

        fun fromCountryInfo(countryInfo: CountryInfo): InfoResponse = InfoResponse(
            uuid = countryInfo.uuid,
            typeUuid = countryInfo.countryUuid,
            summary = countryInfo.summary,
            description = countryInfo.description
        )

        fun fromThemeInfo(themeInfo: ThemeInfo): InfoResponse = InfoResponse(
            uuid = themeInfo.uuid,
            typeUuid = themeInfo.themeUuid,
            summary = themeInfo.summary,
            description = themeInfo.description
        )
    }
}

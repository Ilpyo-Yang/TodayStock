package dev.todaystock.api.info.dto

import dev.todaystock.api.info.entity.CompanyInfo
import dev.todaystock.api.info.entity.CountryInfo
import dev.todaystock.api.info.entity.ThemeInfo
import java.util.*

class InfoResponse(
    private val uuid: UUID?,
    private val typeUuid: UUID,
    private val summary: String,
    private val info: String
) {
    companion object {
        fun fromCompanyInfoList(companyInfo: List<CompanyInfo>): List<InfoResponse> = companyInfo.map { i -> fromCompanyInfo(i) }

        fun fromCountryInfoList(countryInfo: List<CountryInfo>): List<InfoResponse> = countryInfo.map { i -> fromCountryInfo(i) }

        fun fromThemeInfoList(themeInfo: List<ThemeInfo>): List<InfoResponse> = themeInfo.map { i -> fromThemeInfo(i) }

        fun fromCompanyInfo(companyInfo: CompanyInfo): InfoResponse = InfoResponse(
            uuid = companyInfo.uuid,
            typeUuid = companyInfo.company.uuid!!,
            summary = companyInfo.summary,
            info = companyInfo.info
        )

        fun fromCountryInfo(countryInfo: CountryInfo): InfoResponse = InfoResponse(
            uuid = countryInfo.uuid,
            typeUuid = countryInfo.country.uuid!!,
            summary = countryInfo.summary,
            info = countryInfo.info
        )

        fun fromThemeInfo(themeInfo: ThemeInfo): InfoResponse = InfoResponse(
            uuid = themeInfo.uuid,
            typeUuid = themeInfo.theme.uuid!!,
            summary = themeInfo.summary,
            info = themeInfo.info
        )
    }
}

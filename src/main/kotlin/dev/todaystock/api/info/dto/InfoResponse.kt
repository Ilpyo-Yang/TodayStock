package dev.todaystock.api.info.dto

import dev.todaystock.api.info.entity.CompanyInfo
import dev.todaystock.api.info.entity.CountryInfo
import dev.todaystock.api.info.entity.ThemeInfo
import java.util.*

class InfoResponse(
    val uuid: UUID?,
    val typeUuid: UUID,
    val title: String,
    val link: String,
    val info: String
) {
    companion object {
        fun fromCompanyInfoList(companyInfo: List<CompanyInfo>): List<InfoResponse> = companyInfo.map { i -> fromCompanyInfo(i) }

        fun fromCountryInfoList(countryInfo: List<CountryInfo>): List<InfoResponse> = countryInfo.map { i -> fromCountryInfo(i) }

        fun fromThemeInfoList(themeInfo: List<ThemeInfo>): List<InfoResponse> = themeInfo.map { i -> fromThemeInfo(i) }

        fun fromCompanyInfo(companyInfo: CompanyInfo): InfoResponse = InfoResponse(
            uuid = companyInfo.uuid,
            typeUuid = companyInfo.company.uuid!!,
            title = companyInfo.title,
            link = companyInfo.link,
            info = companyInfo.info
        )

        fun fromCountryInfo(countryInfo: CountryInfo): InfoResponse = InfoResponse(
            uuid = countryInfo.uuid,
            typeUuid = countryInfo.country.uuid!!,
            title = countryInfo.title,
            link = countryInfo.link,
            info = countryInfo.info
        )

        fun fromThemeInfo(themeInfo: ThemeInfo): InfoResponse = InfoResponse(
            uuid = themeInfo.uuid,
            typeUuid = themeInfo.theme.uuid!!,
            title = themeInfo.title,
            link = themeInfo.link,
            info = themeInfo.info
        )
    }
}

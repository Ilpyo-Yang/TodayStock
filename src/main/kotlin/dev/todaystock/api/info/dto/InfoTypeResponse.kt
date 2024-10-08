package dev.todaystock.api.info.dto

import dev.todaystock.api.info.entity.Company
import dev.todaystock.api.info.entity.Country
import dev.todaystock.api.info.entity.Theme
import java.util.*

class InfoTypeResponse(
    val uuid: UUID?,
    val name: String,
    val ticker: String?,
    val profile: String
) {
    companion object {
        fun fromCompanies(companies: List<Company>): List<InfoTypeResponse> = companies.map { i -> fromCompany(i) }

        fun fromCountries(countries: List<Country>): List<InfoTypeResponse> = countries.map { i -> fromCountry(i) }

        fun fromThemes(themes: List<Theme>): List<InfoTypeResponse> = themes.map { i -> fromTheme(i) }

        fun fromCompany(company: Company): InfoTypeResponse = InfoTypeResponse(
            uuid = company.uuid,
            name = company.name,
            ticker = company.ticker,
            profile = company.profile
        )

        fun fromCountry(country: Country): InfoTypeResponse = InfoTypeResponse(
            uuid = country.uuid,
            name = country.name,
            ticker = "",
            profile = country.profile
        )

        fun fromTheme(theme: Theme): InfoTypeResponse = InfoTypeResponse(
            uuid = theme.uuid,
            name = theme.name,
            ticker = "",
            profile = theme.profile
        )
    }
}

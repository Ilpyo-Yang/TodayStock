package dev.todaystock.api.dto.infoType

import dev.todaystock.api.entity.Company
import dev.todaystock.api.entity.Country
import dev.todaystock.api.entity.Theme
import java.util.*

class InfoTypeResponse(
    var uuid: UUID?,
    var name: String,
    var profile: String
) {
    companion object {
        fun fromCompany(company: Company): InfoTypeResponse = InfoTypeResponse(
            uuid = company.uuid,
            name = company.name,
            profile = company.profile
        )

        fun fromCountry(country: Country): InfoTypeResponse = InfoTypeResponse(
            uuid = country.uuid,
            name = country.name,
            profile = country.profile
        )

        fun fromTheme(theme: Theme): InfoTypeResponse = InfoTypeResponse(
            uuid = theme.uuid,
            name = theme.name,
            profile = theme.profile
        )
    }
}

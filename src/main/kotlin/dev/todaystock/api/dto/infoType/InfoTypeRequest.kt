package dev.todaystock.api.dto.infoType

import dev.todaystock.api.entity.Company
import dev.todaystock.api.entity.Country
import dev.todaystock.api.entity.Theme
import java.util.*

class InfoTypeRequest(
    var uuid: UUID?,
    var name: String,
    var profile: String
) {
    companion object {
        fun toCompany(infoTypeRequest: InfoTypeRequest): Company = Company(
            uuid = infoTypeRequest.uuid,
            name = infoTypeRequest.name,
            profile = infoTypeRequest.profile
        )

        fun toCountry(infoTypeRequest: InfoTypeRequest): Country = Country(
            uuid = infoTypeRequest.uuid,
            name = infoTypeRequest.name,
            profile = infoTypeRequest.profile
        )

        fun toTheme(infoTypeRequest: InfoTypeRequest): Theme = Theme(
            uuid = infoTypeRequest.uuid,
            name = infoTypeRequest.name,
            profile = infoTypeRequest.profile
        )
    }
}

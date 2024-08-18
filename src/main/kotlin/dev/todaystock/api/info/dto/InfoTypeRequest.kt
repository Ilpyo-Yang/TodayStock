package dev.todaystock.api.info.dto

import dev.todaystock.api.info.entity.Company
import dev.todaystock.api.info.entity.Country
import dev.todaystock.api.info.entity.Theme
import java.util.*

class InfoTypeRequest(
    var uuid: UUID?,
    var name: String,
    var profile: String
) {
    companion object {
        fun toCompany(infoRequest: InfoTypeRequest): Company = Company(
            uuid = infoRequest.uuid ,
            name = infoRequest.name,
            profile = infoRequest.profile
        )

        fun toCountry(infoRequest: InfoTypeRequest): Country = Country(
            uuid = infoRequest.uuid ,
            name = infoRequest.name,
            profile = infoRequest.profile
        )

        fun toTheme(infoRequest: InfoTypeRequest): Theme = Theme(
            uuid = infoRequest.uuid ,
            name = infoRequest.name,
            profile = infoRequest.profile
        )
    }
}

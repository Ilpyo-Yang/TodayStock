package dev.todaystock.api.info.dto

import com.fasterxml.jackson.annotation.JsonProperty
import dev.todaystock.api.info.entity.Company
import dev.todaystock.api.info.entity.Country
import dev.todaystock.api.info.entity.Theme
import jakarta.validation.constraints.NotBlank
import java.util.*

class InfoTypeRequest(
    @JsonProperty(value = "uuid")
    private val _uuid: String?,

    @JsonProperty(value = "name")
    @field:NotBlank(message = "InfoType name cannot be empty")
    private val _name: String,

    @JsonProperty(value = "ticker")
    private val _ticker: String?,

    @JsonProperty(value = "profile")
    @field:NotBlank(message = "Profile cannot be empty")
    private val _profile: String
) {
    val uuid: UUID?
        get() = _uuid?.let { UUID.fromString(_uuid) }
    val name: String
        get() = _name
    val ticker: String?
        get() = _ticker
    val profile: String
        get() = _profile

    companion object {
        fun toCompany(infoRequest: InfoTypeRequest): Company = Company(
            uuid = infoRequest.uuid ,
            name = infoRequest.name,
            ticker = infoRequest.ticker.let { "" },
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

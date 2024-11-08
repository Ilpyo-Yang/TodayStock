package dev.todaystock.api.member.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

class ConfirmPasswordRequest(
    @JsonProperty("password")
    @field:NotBlank(message = "Password name cannot be empty")
    private val _password: String
) {
    val password: String
        get() = _password
}

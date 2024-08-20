package dev.todaystock.api.member.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

class SigninRequest(
    @JsonProperty("email")
    @field:NotBlank(message = "Email name cannot be empty")
    private val _email: String,

    @JsonProperty("password")
    @field:NotBlank(message = "Password name cannot be empty")
    private val _password: String
) {
    val email: String
        get() = _email
    val password: String
        get() = _password
}

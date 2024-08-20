package dev.todaystock.api.collect.dto

import com.fasterxml.jackson.annotation.JsonProperty
import dev.todaystock.api.collect.entity.Collect
import dev.todaystock.api.common.annotation.ValidEnum
import dev.todaystock.api.info.entity.InfoType
import jakarta.validation.constraints.NotBlank
import java.util.*

class CollectRequest(
    @JsonProperty("uuid")
    private val _uuid: String?,

    @JsonProperty("memberUuid")
    @field:NotBlank(message = "MemberUuid cannot be empty")
    private val _memberUuid: String,

    @JsonProperty("type")
    @field:NotBlank(message = "InfoType cannot be empty")
    @field:ValidEnum(enumClass = InfoType::class, message = "Choose one of InfoType")
    private val _type: String,

    @JsonProperty("relatedInfoUuid")
    @field:NotBlank(message = "RelatedInfoUuid cannot be empty")
    private val _relatedInfoUuid: String,

    @JsonProperty("summary")
    @field:NotBlank(message = "Summary cannot be empty")
    private val _summary: String
) {
    val uuid: UUID?
        get() = _uuid?.let { UUID.fromString(it) }
    val memberUuid: UUID
        get() = UUID.fromString(_memberUuid)
    val type: InfoType
        get() = InfoType.valueOf(_type)
    val relatedInfoUuid: UUID
        get() = UUID.fromString(_relatedInfoUuid)
    val summary: String
        get() = _summary

    companion object {
        fun toCollect(collectRequest: CollectRequest): Collect = Collect(
            uuid = collectRequest.uuid,
            memberUuid = collectRequest.memberUuid,
            type = collectRequest.type.name,
            relatedInfoUuid = collectRequest.relatedInfoUuid,
            summary = collectRequest.summary,
        )
    }
}

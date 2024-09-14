package dev.todaystock.api.collect.dto

import dev.todaystock.api.collect.entity.Collect
import dev.todaystock.api.info.entity.InfoType
import java.util.*

class CollectResponse(
    val uuid: UUID?,
    val memberUuid: UUID,
    val type: InfoType,
    val relatedInfoUuid: UUID
) {
    companion object {
        fun fromCollect(collect: Collect): CollectResponse = CollectResponse(
            uuid = collect.uuid,
            memberUuid = collect.memberUuid,
            type = InfoType.valueOf(collect.type),
            relatedInfoUuid = collect.relatedInfoUuid
        )
    }
}

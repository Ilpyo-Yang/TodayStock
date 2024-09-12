package dev.todaystock.api.collect.dto

import dev.todaystock.api.collect.entity.Collect
import dev.todaystock.api.info.entity.InfoType
import java.util.*

class CollectResponse(
    private val uuid: UUID?,
    private val memberUuid: UUID,
    private val type: InfoType,
    private val relatedInfoUuid: UUID
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

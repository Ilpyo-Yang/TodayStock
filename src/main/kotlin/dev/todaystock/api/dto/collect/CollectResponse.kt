package dev.todaystock.api.dto.collect

import dev.todaystock.api.entity.Collect
import dev.todaystock.api.entity.type.InfoType
import java.util.*

class CollectResponse(
    var uuid: UUID?,
    var userUuid: UUID,
    var type: InfoType,
    var relatedInfoUuid: UUID,
    var summary: String
) {
    companion object {
        fun fromCollect(collect: Collect): CollectResponse = CollectResponse(
            uuid = collect.uuid,
            userUuid = collect.userUuid,
            type = InfoType.valueOf(collect.type),
            relatedInfoUuid = collect.relatedInfoUuid,
            summary = collect.summary
        )
    }
}

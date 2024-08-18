package dev.todaystock.api.collect.dto

import dev.todaystock.api.collect.entity.Collect
import dev.todaystock.api.info.entity.InfoType
import java.util.*

class CollectRequest(
    var uuid: UUID?,
    var userUuid: UUID,
    var type: InfoType,
    var relatedInfoUuid: UUID,
    var summary: String
) {
    companion object {
        fun toCollect(collectRequest: CollectRequest): Collect = Collect(
            uuid = collectRequest.uuid,
            userUuid = collectRequest.userUuid,
            type = collectRequest.type.name,
            relatedInfoUuid = collectRequest.relatedInfoUuid,
            summary = collectRequest.summary,
        )
    }
}

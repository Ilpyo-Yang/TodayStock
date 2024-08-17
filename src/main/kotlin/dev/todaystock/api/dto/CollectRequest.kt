package dev.todaystock.api.dto

import dev.todaystock.api.entity.Collect
import dev.todaystock.api.util.InfoType
import java.util.*

class CollectRequest(
    private var uuid: UUID,
    private var userUuid: UUID,
    private var type: InfoType,
    private var relatedInfoUuid: String,
    private var summary: String
) {
    companion object {
        fun toCollect(collectRequest: CollectRequest): Collect = Collect(
            uuid = collectRequest.uuid,
            userUuid = collectRequest.userUuid,
            type = collectRequest.type,
            relatedInfoUuid = collectRequest.relatedInfoUuid,
            summary = collectRequest.summary
        )
    }
}

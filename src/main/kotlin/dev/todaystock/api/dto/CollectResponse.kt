package dev.todaystock.api.dto

import dev.todaystock.api.entity.Collect
import dev.todaystock.api.util.InfoType
import java.util.*

class CollectResponse(
    private var uuid: UUID,
    private var userUuid: UUID,
    private var type: InfoType,
    private var relatedInfoUuid: String,
    private var summary: String
) {
    companion object {
        fun fromCollect(collect: Collect): CollectResponse = CollectResponse(
            uuid = collect.uuid,
            userUuid = collect.userUuid,
            type = collect.type,
            relatedInfoUuid = collect.relatedInfoUuid,
            summary = collect.summary
        )
    }
}

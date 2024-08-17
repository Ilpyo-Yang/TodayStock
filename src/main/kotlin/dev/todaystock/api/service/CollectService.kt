package dev.todaystock.api.service

import dev.todaystock.api.dto.CollectRequest
import dev.todaystock.api.dto.CollectResponse
import dev.todaystock.api.entity.Collect
import dev.todaystock.api.repository.CollectRepository
import dev.todaystock.api.util.InfoType
import org.springframework.stereotype.Service
import java.util.*

@Service
class CollectService(
    private val collectRepository: CollectRepository
) {
    fun findByUserUuid(userUuid: UUID): List<InfoType?> {
        return collectRepository.findAll {
            select(
                path(Collect::type)
            ).from(
                entity(Collect::class)
            ).whereAnd(
                path(Collect::userUuid).eq(userUuid)
            ).groupBy(
                path(Collect::type)
            )
        }
    }

    fun findByUserUuidAndType(user_uuid: UUID, infoType: InfoType): List<Any> {
        return collectRepository.findByUserUuidAndType(user_uuid, infoType)
    }

    fun create(collectRequest: CollectRequest): CollectResponse? {
        return CollectResponse.fromCollect(collectRepository.save(CollectRequest.toCollect(collectRequest)))
    }

    fun edit(collectRequest: CollectRequest): CollectResponse? {
        return CollectResponse.fromCollect(collectRepository.save(CollectRequest.toCollect(collectRequest)))
    }

    fun delete(uuid: UUID): Boolean {
        collectRepository.deleteById(uuid)
        return true
    }
}
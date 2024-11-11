package dev.todaystock.api.collect.service

import dev.todaystock.api.collect.dto.CollectRequest
import dev.todaystock.api.collect.dto.CollectResponse
import dev.todaystock.api.collect.entity.Collect
import dev.todaystock.api.collect.repository.CollectRepository
import dev.todaystock.api.info.entity.InfoType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
class CollectService(
    private val collectRepository: CollectRepository
) {
    fun findByMemberUuid(memberUuid: UUID): List<String?> {
        return collectRepository.findAll {
            select(
                path(Collect::type)
            ).from(
                entity(Collect::class)
            ).whereAnd(
                path(Collect::memberUuid).eq(memberUuid)
            ).groupBy(
                path(Collect::type)
            )
        }
    }

    fun findByMemberUuidAndType(memberUuid: UUID, infoType: InfoType): List<Collect?> {
        return collectRepository.findAll {
            select(
                entity(Collect::class)
            ).from(
                entity(Collect::class)
            ).whereAnd(
                path(Collect::memberUuid).eq(memberUuid),
                path(Collect::type).eq(infoType.name)
            )
        }
    }

    fun create(collectRequest: CollectRequest): CollectResponse? {
        return CollectResponse.fromCollect(collectRepository.save(CollectRequest.toCollect(collectRequest)))
    }

//    fun edit(collectRequest: CollectRequest): CollectResponse {
//        val collect = collectRequest.uuid?.let { collectRepository.findById(it) }
//        if (collect != null && collect.isPresent) {
//            collect.get().apply {
//                memberUuid = collectRequest.memberUuid
//                type = collectRequest.type.name
//                relatedInfoUuid = collectRequest.relatedInfoUuid
//                summary = collectRequest.summary
//            }
//            return CollectResponse.fromCollect(collect.get())
//        } else {
//            throw IllegalArgumentException("Collect not found")
//        }
//    }

    @Transactional
    fun delete(uuid: UUID): Boolean {
        val collect = collectRepository.findById(uuid)
        if (collect.isPresent) {
            collect.get().deletedDate = LocalDateTime.now()
            return true
        } else {
            throw IllegalArgumentException("Collect not found")
        }
    }
}
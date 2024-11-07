package dev.todaystock.api.common.entity

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseEntity (
    @CreatedDate
    var createdDate: LocalDateTime? = null,
    @LastModifiedDate
    var modifiedDate: LocalDateTime? = null,
    var _deletedDate: LocalDateTime? = null
) {
    var deletedDate: LocalDateTime?
        get() = _deletedDate
        set(now: LocalDateTime?) { _deletedDate = LocalDateTime.now() }
}
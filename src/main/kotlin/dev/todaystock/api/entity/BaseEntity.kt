package dev.todaystock.api.entity

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.sql.Timestamp

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseEntity {
    @CreatedDate
    var createdDate: Timestamp? = null
    @LastModifiedDate
    var modifiedDate: Timestamp? = null
    var deletedDate: Timestamp? = null
}
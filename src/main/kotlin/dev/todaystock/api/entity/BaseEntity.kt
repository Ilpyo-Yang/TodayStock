package dev.todaystock.api.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.sql.Timestamp

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseEntity {
    @CreatedDate
    var createdDate: Timestamp? = null
    @LastModifiedBy
    var modifiedDate: Timestamp? = null
}
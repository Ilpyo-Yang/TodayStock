package dev.todaystock.api.entity

import dev.todaystock.api.util.InfoType
import jakarta.persistence.*
import lombok.AllArgsConstructor
import java.util.*

@Entity
@Table(name = "collect")
@AllArgsConstructor
class Collect(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var uuid: UUID,
    var userUuid: UUID,
    var type: InfoType,
    var relatedInfoUuid: String,
    var summary: String
): BaseEntity() {

}
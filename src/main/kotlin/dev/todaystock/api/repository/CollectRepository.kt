package dev.todaystock.api.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import dev.todaystock.api.entity.Collect
import dev.todaystock.api.util.InfoType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CollectRepository: JpaRepository<Collect, UUID>, KotlinJdslJpqlExecutor {
//    fun findByUserUuid(userUuid: UUID): List<InfoType>
    fun findByUserUuidAndType(userUuid: UUID, type: InfoType): List<Collect>
}
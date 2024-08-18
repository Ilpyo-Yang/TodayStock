package dev.todaystock.api.collect.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import dev.todaystock.api.collect.entity.Collect
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CollectRepository: JpaRepository<Collect, UUID>, KotlinJdslJpqlExecutor {
}
package dev.todaystock.api.controller

import dev.todaystock.api.service.CollectService
import dev.todaystock.api.dto.CollectRequest
import dev.todaystock.api.util.InfoType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/collect")
class CollectController (
    private val collectService: CollectService
) {
    @GetMapping
    fun findByUserUuid(@RequestParam userUuid: UUID): ResponseEntity<Any> = ResponseEntity.ok().body(collectService.findByUserUuid(userUuid))

    @GetMapping("/type")
    fun findByUserUuidAndType(@RequestParam user_uuid: UUID,
                              @RequestParam infoType: String
    ): ResponseEntity<Any>
    {
        return ResponseEntity.ok().body(collectService.findByUserUuidAndType(user_uuid, InfoType.valueOf(infoType)))
    }

    @PostMapping
    fun create(@RequestBody request: CollectRequest): ResponseEntity<Any> = ResponseEntity.ok().body(collectService.create(request))

    @PutMapping
    fun edit(@RequestBody request: CollectRequest): ResponseEntity<Any> = ResponseEntity.ok().body(collectService.edit(request))

    @DeleteMapping
    fun delete(@RequestBody uuid: UUID): ResponseEntity<Any> = ResponseEntity.ok().body(collectService.delete(uuid))

}
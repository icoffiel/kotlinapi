package com.icoffiel.kotlinapi.platform

import com.icoffiel.kotlinapi.platform.dto.PlatformAddRequest
import com.icoffiel.kotlinapi.platform.dto.PlatformApiResponse
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/platforms")
class PlatformController(
    private val platformService: PlatformService
) {
    @GetMapping
    fun listPlatforms(): List<PlatformApiResponse> = platformService.findAllPlatforms()

    @GetMapping("/{id}")
    fun getPlatform(@PathVariable id: Long): PlatformApiResponse = platformService.findById(id)

    @PostMapping
    fun addPlatform(@RequestBody platform: PlatformAddRequest): PlatformApiResponse = platformService.save(platform)

    @DeleteMapping("/{id}")
    fun deletePlatform(@PathVariable id: Long) = platformService.delete(id)
}

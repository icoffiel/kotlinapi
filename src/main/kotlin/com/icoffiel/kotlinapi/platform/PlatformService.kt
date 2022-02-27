package com.icoffiel.kotlinapi.platform

import com.icoffiel.kotlinapi.common.exception.NoEntityFoundException
import com.icoffiel.kotlinapi.platform.dto.PlatformAddRequest
import com.icoffiel.kotlinapi.platform.dto.PlatformApiResponse
import com.icoffiel.kotlinapi.platform.dto.toPlatformApiResponse
import com.icoffiel.kotlinapi.platform.dto.toPlatformEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PlatformService(
    private val platformRepository: PlatformRepository
) {
    fun findAllPlatforms(): List<PlatformApiResponse> =
        platformRepository
            .findAll()
            .map { it.toPlatformApiResponse() }

    fun save(platformAddRequest: PlatformAddRequest): PlatformApiResponse =
        platformRepository
            .save(platformAddRequest.toPlatformEntity())
            .toPlatformApiResponse()

    fun delete(id: Long) =
        platformRepository.deleteById(id)

    fun findById(id: Long): PlatformApiResponse =
        platformRepository
            .findByIdOrNull(id)
            ?.toPlatformApiResponse()
            ?: throw NoEntityFoundException(id, "Platform")
}

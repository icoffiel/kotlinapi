package com.icoffiel.kotlinapi.platform.dto

import com.icoffiel.kotlinapi.platform.domain.PlatformEntity
import java.security.InvalidParameterException

fun PlatformEntity.toPlatformApiResponse(): PlatformApiResponse = PlatformApiResponse(
    id = this.id ?: throw InvalidParameterException("Unable to map null id"),
    name = this.name,
    releaseDate = this.releaseDate,

)

fun PlatformAddRequest.toPlatformEntity(): PlatformEntity = PlatformEntity(
    name = this.name,
    releaseDate = this.releaseDate,
)

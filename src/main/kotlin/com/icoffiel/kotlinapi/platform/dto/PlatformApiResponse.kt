package com.icoffiel.kotlinapi.platform.dto

import java.time.LocalDate

data class PlatformApiResponse(
    val id: Long,
    val name: String,
    val releaseDate: LocalDate,
)

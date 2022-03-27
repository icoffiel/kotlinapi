package com.icoffiel.kotlinapi.platform.dto

import java.time.LocalDate

data class PlatformAddRequest(
    val name: String,
    val releaseDate: LocalDate,
)

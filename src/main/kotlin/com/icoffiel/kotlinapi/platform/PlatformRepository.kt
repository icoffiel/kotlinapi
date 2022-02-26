package com.icoffiel.kotlinapi.platform

import com.icoffiel.kotlinapi.platform.domain.PlatformEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PlatformRepository : JpaRepository<PlatformEntity, Long> {}

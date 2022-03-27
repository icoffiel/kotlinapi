package com.icoffiel.kotlinapi.platform.dto

import com.icoffiel.kotlinapi.platform.domain.PlatformEntity
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import java.security.InvalidParameterException
import java.time.LocalDate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlatformAdapterTest {
    @Test
    fun `PlatformAdapter can convert from a PlatformEntity to a PlatformApiResponse`() {

        val platformEntity = PlatformEntity(
            id = 100L,
            name = "Name",
            releaseDate = LocalDate.parse("2020-01-01"),
        )

        val result = platformEntity.toPlatformApiResponse()

        assertThat(
            result,
            equalTo(
                PlatformApiResponse(
                    id = 100L,
                    name = "Name",
                    releaseDate = LocalDate.parse("2020-01-01")
                )
            )
        )
    }

    @Test
    fun `PlatformAdapter cannot convert from a PlatFormEntity to a PlatformApiResponse when there is a null id`() {
        val platformEntity = PlatformEntity(
            name = "Name",
            releaseDate = LocalDate.parse("2020-01-01"),
        )

        assertThrows<InvalidParameterException> {
            platformEntity.toPlatformApiResponse()
        }
    }

    @Test
    fun `PlatformAdapter can convert from a PlatformAddRequest to a PlatformEntity`() {
        val releaseDate = LocalDate.parse("2020-01-01")
        val platformAddRequest = PlatformAddRequest(name = "Xbox", releaseDate = releaseDate)

        val result = platformAddRequest.toPlatformEntity()

        assertThat(
            result,
            equalTo(
                PlatformEntity(
                    name = "Xbox",
                    releaseDate = releaseDate
                )
            )
        )
    }
}
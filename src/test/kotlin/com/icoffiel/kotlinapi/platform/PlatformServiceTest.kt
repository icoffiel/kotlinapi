package com.icoffiel.kotlinapi.platform

import com.icoffiel.kotlinapi.common.exception.NoEntityFoundException
import com.icoffiel.kotlinapi.platform.domain.PlatformEntity
import com.icoffiel.kotlinapi.platform.dto.PlatformAddRequest
import com.icoffiel.kotlinapi.platform.dto.PlatformApiResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlatformServiceTest {
    private val platformRepository: PlatformRepository = mockk()
    private val platformService: PlatformService = PlatformService(platformRepository)

    @Test
    fun `when findAllPlatforms is called return the expected platforms`() {
        val expectedPlatforms = listOf(
            PlatformApiResponse(id = 1, name = "Xbox", releaseDate = LocalDate.parse("2020-01-01")),
        )

        every { platformRepository.findAll() } returns listOf(
            PlatformEntity(id = 1, name = "Xbox", releaseDate = LocalDate.parse("2020-01-01"))
        )

        val platforms = platformService.findAllPlatforms()

        assertThat(platforms.size, `is`(equalTo(expectedPlatforms.size)))
        assertThat(platforms, `is`(equalTo(expectedPlatforms)))
    }

    @Test
    fun `PlatformService findById returns the expected PlatformAPIResponse`() {
        val releaseDate = LocalDate.parse("2020-01-01")
        val platformEntity = PlatformEntity(
            id = 1,
            name = "Xbox",
            releaseDate = releaseDate
        )

        every { platformRepository.findByIdOrNull(any()) } returns platformEntity

        val platformAPIResponse = platformService.findById(1)

        assertThat(platformAPIResponse.id, equalTo(platformEntity.id))
        assertThat(platformAPIResponse.name, equalTo(platformEntity.name))
        assertThat(platformAPIResponse.releaseDate, equalTo(releaseDate))
    }

    @Test
    fun `PlatformService findById throws an exception if no platform is found with an id`() {
        every { platformRepository.findByIdOrNull(any()) } answers { nothing }

        val exception = assertThrows<NoEntityFoundException> {
            platformService.findById(1)
        }

        assertThat(exception.message, equalTo("Platform was not found for id 1"))
    }

    @Test
    fun `Platform service save will return the expected PlatformAPIResponse`() {
        val releaseDate = LocalDate.parse("2020-01-01")
        val platformAddRequest = PlatformAddRequest(name = "Xbox", releaseDate = releaseDate)

        every { platformRepository.save(any()) } returns PlatformEntity(
            id = 1,
            name = platformAddRequest.name,
            releaseDate = releaseDate
        )

        val returnedPlatform = platformService.save(platformAddRequest)

        assertThat(returnedPlatform.id, notNullValue())
        assertThat(returnedPlatform.name, equalTo(platformAddRequest.name))
        assertThat(returnedPlatform.releaseDate, equalTo(releaseDate))
    }

    @Test
    fun `PlatformService delete will delete the expected PlatformEntity`() {
        every { platformRepository.deleteById(any()) } answers { nothing }

        platformService.delete(1)

        verify { platformRepository.deleteById(1) }
    }
}

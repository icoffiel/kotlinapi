package com.icoffiel.kotlinapi.platform

import com.icoffiel.kotlinapi.platform.domain.PlatformEntity
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDate

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlatformRepositoryTest(
    @Autowired
    private val entityManager: TestEntityManager,

    @Autowired
    private val platformRepository: PlatformRepository,
) {
    @Test
    fun `PlatformRepository findAll returns all saved platforms`() {
        val releaseDate = LocalDate.parse("2020-01-01")

        val expectedPlatforms = listOf(
            PlatformEntity(
                name = "Xbox",
                releaseDate = releaseDate,
            ),
            PlatformEntity(
                name = "Playstation",
                releaseDate = releaseDate,
            ),
        )

        expectedPlatforms.forEach { entityManager.persist(it) }

        val platforms = platformRepository.findAll()

        assertThat(platforms.size, `is`(equalTo(expectedPlatforms.size)))

        expectedPlatforms
            .forEach { expected ->
                assertThat(platforms, hasItem<PlatformEntity>(hasProperty("name", `is`(expected.name))))
                assertThat(platforms, hasItem<PlatformEntity>(hasProperty("releaseDate", `is`(expected.releaseDate))))
            }
    }

    @Test
    fun `PlatformRepository find returns a PlatformEntity`() {
        val releaseDate = LocalDate.now()
        val savedEntity = entityManager.persist(
            PlatformEntity(
                name = "Xbox",
                releaseDate = releaseDate,
            )
        )

        val platform = platformRepository.findByIdOrNull(savedEntity.id)

        assertThat(platform?.id, equalTo(savedEntity.id))
        assertThat(platform?.name, equalTo(savedEntity.name))
        assertThat(platform?.releaseDate, equalTo(savedEntity.releaseDate))
    }

    @Test
    fun `PlatformRepository save returns the saved platform`() {
        val releaseDate = LocalDate.parse("2020-01-01")
        val platformEntity = PlatformEntity(
            name = "Xbox",
            releaseDate = releaseDate
        )

        val savedPlatformEntity = platformRepository.save(platformEntity)

        assertThat(savedPlatformEntity.id, notNullValue())
        assertThat(savedPlatformEntity.name, equalTo("Xbox"))
        assertThat(savedPlatformEntity.releaseDate, equalTo(releaseDate))
    }

    @Test
    fun `PlatformRepository delete removes the saved platform`() {
        val savedPlatform = entityManager.persist(
            PlatformEntity(
                name = "Xbox",
                releaseDate = LocalDate.parse("2020-01-01"),
            )
        )
        assertThat(entityManager.find(PlatformEntity::class.java, savedPlatform.id), notNullValue())

        platformRepository.deleteById(savedPlatform.id!!)
        assertThat(entityManager.find(PlatformEntity::class.java, savedPlatform.id), nullValue())
    }
}

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
        val expectedPlatforms = listOf(
            PlatformEntity(name = "Xbox"),
            PlatformEntity(name = "Playstation")
        )

        expectedPlatforms.forEach { entityManager.persist(it) }

        val platforms = platformRepository.findAll()

        assertThat(platforms.size, `is`(equalTo(expectedPlatforms.size)))

        expectedPlatforms
            .forEach { expected ->
                assertThat(platforms, hasItem<PlatformEntity>(hasProperty("name", `is`(expected.name))))
            }
    }

    @Test
    fun `PlatformRepository find returns a PlatformEntity`() {
        val savedEntity = entityManager.persist(
            PlatformEntity(
                name = "Xbox"
            )
        )

        val platform = platformRepository.findByIdOrNull(savedEntity.id)

        assertThat(platform?.id, equalTo(savedEntity.id))
        assertThat(platform?.name, equalTo(savedEntity.name))
    }

    @Test
    fun `PlatformRepository save returns the saved platform`() {
        val platformEntity = PlatformEntity(name = "Xbox")

        val savedPlatformEntity = platformRepository.save(platformEntity)

        assertThat(savedPlatformEntity.id, notNullValue())
        assertThat(savedPlatformEntity.name, equalTo("Xbox"))
    }

    @Test
    fun `PlatformRepository delete removes the saved platform`() {
        val savedPlatform = entityManager.persist(
            PlatformEntity(
                name = "Xbox"
            )
        )
        assertThat(entityManager.find(PlatformEntity::class.java, savedPlatform.id), notNullValue())

        platformRepository.deleteById(savedPlatform.id!!)
        assertThat(entityManager.find(PlatformEntity::class.java, savedPlatform.id), nullValue())
    }
}

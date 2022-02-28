package com.icoffiel.kotlinapi.platform

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.icoffiel.kotlinapi.common.exception.NoEntityFoundException
import com.icoffiel.kotlinapi.platform.dto.PlatformAddRequest
import com.icoffiel.kotlinapi.platform.dto.PlatformApiResponse
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.hamcrest.Matchers.*
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@WebMvcTest(PlatformController::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlatformControllerTest(
    @Autowired private val mockMvc: MockMvc,
) {
    @MockkBean
    lateinit var platformService: PlatformService // TODO - Can we get rid of the lateinit var?

    private val mapper = jacksonObjectMapper()

    @Test
    fun `Platform Controller returns the platforms successfully`() {
        every { platformService.findAllPlatforms() } returns listOf()

        mockMvc
            .get("/platforms")
            .andExpect {
                status { is2xxSuccessful() }
                content { contentType(MediaType.APPLICATION_JSON) }
                content { json("[]") }
            }
    }

    @Test
    fun `PlatformController returns a platform successfully`() {
        every { platformService.findById(any()) } returns PlatformApiResponse(
            id = 1,
            name = "Xbox"
        )

        @Language("JSON") val expectedJson = """
            {
              "id": 1,
              "name": "Xbox"
            }
        """.trimIndent()

        mockMvc
            .get("/platforms/{id}", 1)
            .andExpect {
                status { is2xxSuccessful() }
                content { contentType(MediaType.APPLICATION_JSON) }
                content { json(expectedJson) }
            }
    }

    @Test
    fun `PlatformController returns a 404 when no item is found`() {
        every { platformService.findById(any()) } throws NoEntityFoundException(1, "Platform")

        mockMvc
            .get("/platforms/{id}", 1)
            .andExpect {
                status { isNotFound() }
            }
    }

    @Test
    fun `Platform Controller can add a platform successfully`() {
        val addRequest = PlatformAddRequest(name = "Xbox")

        every { platformService.save(any()) } returns PlatformApiResponse(id = 1, name = addRequest.name)

        mockMvc
            .post("/platforms") {
                contentType = MediaType.APPLICATION_JSON
                content = mapper.writeValueAsString(addRequest)
            }
            .andExpect {
                status { is2xxSuccessful() }
                content { contentType(MediaType.APPLICATION_JSON) }
                content { jsonPath("$.id", notNullValue()) }
                content { jsonPath("$.name", equalTo("Xbox")) }
            }
    }

    @Test
    fun `PlatformController can delete a platform successfully`() {
        every { platformService.delete(any()) } answers { nothing }
        mockMvc
            .delete("/platforms/{id}", 1)
            .andExpect {
                status { is2xxSuccessful() }
            }
    }
}

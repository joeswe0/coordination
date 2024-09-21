package com.commerce.coordination.api.controller.v1

import com.commerce.coordination.brand.BrandRepository
import com.commerce.coordination.category.Category
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class BrandProductControllerIntegrationTest {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var brandRepository: BrandRepository  // 브랜드 데이터를 저장하기 위한 리포지토리

    private val objectMapper = jacksonObjectMapper()


    @Test
    fun `요청한 프로덕트가 브랜드에 입력되어 있다`() {
        val brand = brandRepository.createBrand("Test")
        // Given
        val request = BrandProductUpdateRequest(
            products = listOf(
                ProductDto(price = 10000, category = Category.TOP),
                ProductDto(price = 5000, category = Category.OUTERWEAR)
            )
        )

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val entity = HttpEntity(objectMapper.writeValueAsString(request), headers)
        val brandId = brand.id
        // When
        val response: ResponseEntity<String> = restTemplate.postForEntity(
            "http://localhost:$port/api/v1/brands/" + brandId + "/products", entity, String::class.java
        )

        // Then
        assertEquals(HttpStatus.OK, response.statusCode)

        val responseBody = objectMapper.readValue<ApiResponse<BrandProductUpdateResponse>>(response.body!!).data
            ?: throw IllegalStateException()
        assertEquals(brand.id, responseBody.id)
        assertEquals(brand.name, responseBody.name)
        assertEquals(2, responseBody.products.size)

        responseBody.products.first { it.category == Category.TOP }.let {
            assertEquals(10000, it.price)
        }

        responseBody.products.first { it.category == Category.OUTERWEAR }.let {
            assertEquals(5000, it.price)
        }
    }
}

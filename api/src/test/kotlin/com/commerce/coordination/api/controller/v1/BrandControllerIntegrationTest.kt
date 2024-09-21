package com.commerce.coordination.api.controller.v1


import com.commerce.coordination.controller.v1.ApiResponse
import com.commerce.coordination.controller.v1.BrandCreateRequest
import com.commerce.coordination.controller.v1.BrandCreateResponse
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
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
class BrandControllerIntegrationTest {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private val objectMapper = jacksonObjectMapper()

    @Test
    fun `브랜드 생성 테스트`() {
        // Given
        val brandCreateRequest = BrandCreateRequest(name = "Test Brand")

        // HTTP 요청 설정
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val request = HttpEntity(brandCreateRequest, headers)

        // 실제 서버에 POST 요청을 전송
        val response: ResponseEntity<String> = restTemplate.postForEntity(
            "http://localhost:$port/api/v1/brands", request, String::class.java
        )

        // Then: 응답 코드와 내용 검증
        assert(response.statusCode == HttpStatus.OK)

        val responseBody = objectMapper.readValue<ApiResponse<BrandCreateResponse>>(response.body!!)
        assert(responseBody.data?.name == "Test Brand")
    }
}

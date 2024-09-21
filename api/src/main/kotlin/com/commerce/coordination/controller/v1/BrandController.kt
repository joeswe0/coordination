package com.commerce.coordination.controller.v1

import com.commerce.coordination.brand.BrandService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/brands")
class BrandController(val brandService: BrandService) {
    @PostMapping
    @Operation(summary = "브랜드 등록", description = "브랜드를 등록합니다.")
    fun createBrand(
        @RequestBody brandCreateRequest: BrandCreateRequest
    ): ApiResponse<BrandCreateResponse> {
        val created = brandService.createBrand(brandCreateRequest.name)
        return ApiResponse.success(BrandCreateResponse(id = created.id, name = created.name))
    }
}

data class BrandCreateRequest(
    @Schema(description = "브랜드 명")
    val name: String
)

data class BrandCreateResponse(
    @Schema(description = "브랜드 ID")
    val id: Long,
    @Schema(description = "브랜드 명")
    val name: String
)

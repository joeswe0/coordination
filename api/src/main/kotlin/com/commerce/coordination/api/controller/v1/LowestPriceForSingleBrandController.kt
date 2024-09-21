package com.commerce.coordination.api.controller.v1

import com.commerce.coordination.LowestPriceForSingleBrandService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/products/lowest-price-for-single-brand")
class LowestPriceForSingleBrandController(val lowestPriceForSingleBrandService: LowestPriceForSingleBrandService) {
    @GetMapping
    @Operation(
        summary = "단일 브랜드 최저가 구매 API",
        description = "단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회한다."
    )
    fun lowestPriceForSingleBrand(
    ): ApiResponse<LowestPriceForSingleBrandResponse> {
        return ApiResponse.success(
            lowestPriceForSingleBrandService.getLowestPriceBySingleBrand().let {
                LowestPriceForSingleBrandResponse(
                    categoryPrices = it.categoryPrices.map { categoryPrice ->
                        CategoryPriceResponse(
                            category = categoryPrice.category,
                            brandName = categoryPrice.brandName,
                            amount = categoryPrice.price

                        )
                    },
                    totalAmount = it.totalAmount,
                    brandName = it.brandName
                )
            }
        )
    }
}

data class LowestPriceForSingleBrandResponse(
    @Schema(description = "최저가 브랜드 이름")
    val brandName: String,
    val categoryPrices: List<CategoryPriceResponse>,
    @Schema(description = "총금액")
    val totalAmount: Long
)

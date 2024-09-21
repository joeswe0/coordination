package com.commerce.coordination.api.controller.v1

import com.commerce.coordination.LowestPriceByCategoryService
import com.commerce.coordination.category.Category
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/products/lowest-price-by-Category")
class LowestPriceByCategoryController(val lowestPriceByCategoryService: LowestPriceByCategoryService) {
    @GetMapping
    @Operation(summary= "카테고리 별 최저가 정보 API", description = "카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회한다")
    fun getLowestPriceByCategory(
    ): ApiResponse<LowestPriceByCategoryResponse> {
        return ApiResponse.success(
            lowestPriceByCategoryService.getLowestPriceByCategory().let {
                LowestPriceByCategoryResponse(
                    categoryPrices = it.categoryPrices.map { categoryPrice ->
                        CategoryPriceResponse(
                            category = categoryPrice.category,
                            brandName = categoryPrice.brandName,
                            amount = categoryPrice.price

                        )
                    }, totalAmount = it.totalAmount

                )
            }
        )
    }
}

data class LowestPriceByCategoryResponse(
    val categoryPrices: Collection<CategoryPriceResponse>,
    @Schema(description = "총금액")
    val totalAmount: Long
)

data class CategoryPriceResponse(
    @Schema(description = "카테고리")
    val category: Category,
    @Schema(description = "브랜드 이름")
    val brandName: String,
    @Schema(description = "가격")
    val amount: Long
)

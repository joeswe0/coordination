package com.commerce.coordination.api.controller.v1

import com.commerce.coordination.CategoryMinMaxPricesService
import com.commerce.coordination.category.Category
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/products/category-min-max-prices")
class CategoryMinMaxPricesController {
    @GetMapping("/{categoryName}")
    @Operation(summary = "카테고리 내 최저, 최고가 정보 API", description = "카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회한다.")
    fun getCategoryMinMaxPrices(
        @PathVariable("categoryName") catalogName: String,
        categoryMinMaxPricesService: CategoryMinMaxPricesService
    ): ApiResponse<CategoryMinMaxPriceResponse> {
        return ApiResponse.success(
            categoryMinMaxPricesService.getCategoryMinMaxPrices(catalogName).let {
                CategoryMinMaxPriceResponse(
                    category = it.category,
                    lowestPrice = BrandPriceResponse(
                        brandName = it.lowestPrice.brandName,
                        price = it.lowestPrice.price
                    ),
                    highestPrice = BrandPriceResponse(
                        brandName = it.highestPrice.brandName,
                        price = it.highestPrice.price
                    )

                )
            }
        )
    }
}

data class CategoryMinMaxPriceResponse(
    @Schema(description = "카테고리명")
    val category: Category,
    @Schema(description = "최저가")
    val lowestPrice: BrandPriceResponse,
    @Schema(description = "최고가")
    val highestPrice: BrandPriceResponse
)

data class BrandPriceResponse(
    @Schema(description = "브랜드 이름")
    val brandName: String,
    @Schema(description = "가격")
    val price: Long
)

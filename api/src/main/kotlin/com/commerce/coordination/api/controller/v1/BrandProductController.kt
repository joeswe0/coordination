package com.commerce.coordination.api.controller.v1

import com.commerce.coordination.brand.BrandProductService
import com.commerce.coordination.category.Category
import com.commerce.coordination.product.Amount
import com.commerce.coordination.product.ProductProps
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/brands/{brandId}/products")
class BrandProductController(val brandProductService: BrandProductService) {
    @PostMapping
    @Operation(summary = "상품 변경 API", description = "브랜드에 상품을 변경 (등록, 업데이트, 삭제)")
    fun createBrand(
        @PathVariable brandId: Long,
        @RequestBody brandProductUpdateRequest: BrandProductUpdateRequest,
    ): ApiResponse<BrandProductUpdateResponse> {
        val updated = brandProductService.updateProducts(
            brandId = brandId,
            productProps = brandProductUpdateRequest.products.map {
                object : ProductProps {
                    override val amount: Amount
                        get() = Amount(it.amount)
                    override val category: Category
                        get() = it.category
                }
            })

        return ApiResponse.success(BrandProductUpdateResponse(
            id = updated.id, name = updated.name, products = updated.products.products.map {
                ProductDto(amount = it.amount.value, category = it.category)
            }
        ))
    }
}

data class BrandProductUpdateRequest(
    val products: List<ProductDto>
)

data class ProductDto(
    @Schema(description = "가격")
    val amount: Long,
    @Schema(description = "카테고리")
    val category: Category,
)

data class BrandProductUpdateResponse(
    @Schema(description = "브랜드 ID")
    val id: Long,
    @Schema(description = "브랜드 명")
    val name: String,

    val products: List<ProductDto>
)



package com.commerce.coordination.brand

import com.commerce.coordination.product.Product
import com.commerce.coordination.product.ProductProps
import com.commerce.coordination.product.Products
import org.springframework.stereotype.Service

@Service
class BrandProductService(
    private val brandRepository: BrandRepository,
) {
    fun updateProducts(
        brandId: Long,
        productProps: List<ProductProps>
    ): Brand {
        checkDuplicatedCategory(productProps)
        val exists: Brand = brandRepository.getBrand(brandId) ?: throw IllegalStateException("브랜드가 존재하지 않습니다.")

        return brandRepository.updateBrandProducts(
            Brand(
                id = exists.id,
                name = exists.name,
                products = Products(products = productProps.map {
                    Product(
                        price = it.price,
                        category = it.category,
                    )
                })
            )
        )
    }

    private fun checkDuplicatedCategory(productProps: List<ProductProps>) {
        val groupByCategory = productProps
            .groupBy { it.category }         // 카테고리별로 그룹핑
            .filter { it.value.size > 1 }
        if (groupByCategory.isNotEmpty()) {
            val duplicateCategories = groupByCategory.keys.joinToString(", ")  // 중복된 카테고리 추출
            throw DuplicateCategoryException("중복된 카테고리 상품이 존재합니다.: $duplicateCategories")
        }
    }

}

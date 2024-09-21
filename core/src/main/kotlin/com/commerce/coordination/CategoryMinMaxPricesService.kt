package com.commerce.coordination

import com.commerce.coordination.brand.BrandRepository
import com.commerce.coordination.category.Category
import com.commerce.coordination.category.enumValueOfOrNull
import org.springframework.stereotype.Service

@Service
class CategoryMinMaxPricesService(val brandRepository: BrandRepository) {
    fun getCategoryMinMaxPrices(categoryName: String): CategoryMinMaxPrice {
        val category = enumValueOfOrNull<Category>(categoryName)
            ?: throw IllegalArgumentException("해당 카테고리는 존재하지 않습니다: $categoryName")
        val brands = brandRepository.getAllBrands()

        val productsByCategory = brands.brands
            .flatMap { brand ->
                brand.products.products.map { product -> brand to product }
            }
            .filter { it.second.category == category }

        val lowestPriceBrand = productsByCategory.minByOrNull { it.second.price.value }
            ?: throw IllegalArgumentException("해당 카테고리에 해당하는 상품이 없습니다: $category")
        val highestPriceBrand = productsByCategory.maxByOrNull { it.second.price.value }
            ?: throw IllegalArgumentException("해당 카테고리에 해당하는 상품이 없습니다: $category")

        return CategoryMinMaxPrice(
            category = category,
            lowestPrice = BrandPrice(
                brandName = lowestPriceBrand.first.name,
                price = lowestPriceBrand.second.price.value
            ),
            highestPrice = BrandPrice(
                brandName = highestPriceBrand.first.name,
                price = highestPriceBrand.second.price.value
            )
        )
    }
}


data class CategoryMinMaxPrice(
    val category: Category,
    val lowestPrice: BrandPrice,
    val highestPrice: BrandPrice
)

data class BrandPrice(
    val brandName: String,
    val price: Long
)

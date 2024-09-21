package com.commerce.coordination

import com.commerce.coordination.brand.BrandRepository
import org.springframework.stereotype.Service

@Service
class LowestPriceByCategoryService(val brandRepository: BrandRepository) {
    fun getLowestPriceByCategory(): LowestPriceByCategory {
        val brands = brandRepository.getAllBrands()

        val lowestPricesByCategory = brands
            .flatMap { brand ->
                brand.products.products.map { product -> product.category to (brand to product.amount.value) }
            }
            .groupBy({ it.first }, { it.second })
            .mapValues { (_, brandProductPairs) ->
                brandProductPairs.minByOrNull { it.second }!!
            }

        val totalAmount = lowestPricesByCategory.values.sumOf { it.second }

        val categoryPriceList = lowestPricesByCategory.map { (category, brandWithPrice) ->
            CategoryPrice(
                category = category,
                brandName = brandWithPrice.first.name,
                price = brandWithPrice.second
            )
        }

        return LowestPriceByCategory(
            categoryPrices = categoryPriceList,
            totalAmount = totalAmount
        )
    }
}

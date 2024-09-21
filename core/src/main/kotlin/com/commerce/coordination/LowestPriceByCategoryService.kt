package com.commerce.coordination

import com.commerce.coordination.brand.BrandRepository
import org.springframework.stereotype.Service

@Service
class LowestPriceByCategoryService(val brandRepository: BrandRepository) {
    fun getLowestPriceByCategory(): LowestPriceByCategory {
        val brands = brandRepository.getAllBrands()

        val lowestPricesByCategory = brands.lowestPricesByCategory()

        val totalPrice = lowestPricesByCategory.values.sumOf { it.second }

        val categoryPriceList = lowestPricesByCategory.map { (category, brandWithPrice) ->
            CategoryPrice(
                category = category,
                brandName = brandWithPrice.first.name,
                price = brandWithPrice.second
            )
        }

        return LowestPriceByCategory(
            categoryPrices = categoryPriceList,
            totalPrice = totalPrice
        )
    }
}

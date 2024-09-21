package com.commerce.coordination

import com.commerce.coordination.brand.BrandRepository
import org.springframework.stereotype.Service

@Service
class LowestPriceForSingleBrandService(val brandRepository: BrandRepository) {
    fun getLowestPriceBySingleBrand(): LowestPriceForSingleBrand {
        val brands = brandRepository.getAllBrands()

        val brandWithTotalPrices = brands.map { brand ->
            val totalAmount = brand.products.products.sumOf { it.amount.value }
            brand to totalAmount
        }

        val (lowestPriceBrand, lowestTotalAmount) = brandWithTotalPrices.minByOrNull { it.second }
            ?: throw IllegalStateException("브랜드가 존재하지 않습니다.")

        val categoryPrices = lowestPriceBrand.products.products.map { product ->
            CategoryPrice(
                category = product.category,
                brandName = lowestPriceBrand.name,
                price = product.amount.value
            )
        }

        return LowestPriceForSingleBrand(
            brandName = lowestPriceBrand.name,
            categoryPrices = categoryPrices,
            totalAmount = lowestTotalAmount
        )
    }
}

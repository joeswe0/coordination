package com.commerce.coordination

import com.commerce.coordination.brand.BrandRepository
import org.springframework.stereotype.Service

@Service
class LowestPriceForSingleBrandService(val brandRepository: BrandRepository) {
    fun getLowestPriceBySingleBrand(): LowestPriceForSingleBrand {
        val brands = brandRepository.getAllBrands()

        val (lowestPriceBrand, lowestTotalPrice) = brands.lowestPriceBrandAndTotalPrice()
            ?: throw IllegalStateException("브랜드가 존재하지 않습니다.")

        val categoryPrices = lowestPriceBrand.products.products.map { product ->
            CategoryPrice(
                category = product.category,
                brandName = lowestPriceBrand.name,
                price = product.price.value
            )
        }

        return LowestPriceForSingleBrand(
            brandName = lowestPriceBrand.name,
            categoryPrices = categoryPrices,
            totalPrice = lowestTotalPrice
        )
    }
}

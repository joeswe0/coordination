package com.commerce.coordination.brand

class Brands(val brands: Collection<Brand>) {
    fun lowestPriceBrandAndTotalPrice() = brands.map { brand ->
        val totalPrice = brand.products.sumOfPrice()
        brand to totalPrice
    }.minByOrNull { it.second }

    fun lowestPricesByCategory() = brands
        .flatMap { brand ->
            brand.products.products.map { product -> product.category to (brand to product.price.value) }
        }
        .groupBy({ it.first }, { it.second })
        .mapValues { (_, brandProductPairs) ->
            brandProductPairs.minByOrNull { it.second } ?: throw IllegalStateException("프로덕트를 찾을 수 없습니다.")
        }

}

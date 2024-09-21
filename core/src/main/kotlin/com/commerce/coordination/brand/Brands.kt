package com.commerce.coordination.brand

class Brands(val brands: Collection<Brand>) {

    fun lowestPriceBrandAndTotalAmount() = brands.map { brand ->
        val totalAmount = brand.products.products.sumOf { it.amount.value }
        brand to totalAmount
    }.minByOrNull { it.second }

    fun lowestPricesByCategory() = brands
        .flatMap { brand ->
            brand.products.products.map { product -> product.category to (brand to product.amount.value) }
        }
        .groupBy({ it.first }, { it.second })
        .mapValues { (_, brandProductPairs) ->
            brandProductPairs.minByOrNull { it.second }!!
        }

}

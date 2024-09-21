package com.commerce.coordination.brand

class Brands(val brands: Collection<Brand>) {

    fun lowestPriceBrandAndTotalAmount() = brands.map { brand ->
        val totalAmount = brand.products.products.sumOf { it.amount.value }
        brand to totalAmount
    }.minByOrNull { it.second }

}

package com.commerce.coordination

data class LowestPriceForSingleBrand(
    val brandName: String,
    val categoryPrices: List<CategoryPrice>,
    val totalPrice: Long
)

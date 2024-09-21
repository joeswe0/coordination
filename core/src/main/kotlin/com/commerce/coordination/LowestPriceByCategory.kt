package com.commerce.coordination

data class LowestPriceByCategory(val categoryPrices: Collection<CategoryPrice>, val totalPrice: Long)

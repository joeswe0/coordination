package com.commerce.coordination.product

import com.commerce.coordination.brand.Brand
import com.commerce.coordination.category.Category

data class Product(
    val id: Long,
    val amount: Amount,
    val category: Category,
    val brand: Brand,
)

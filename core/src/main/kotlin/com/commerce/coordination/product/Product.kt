package com.commerce.coordination.product

import com.commerce.coordination.category.Category

data class Product(
    val id: Long,
    val amount: Amount,
    val brandId: Long,
    val category: Category,
)

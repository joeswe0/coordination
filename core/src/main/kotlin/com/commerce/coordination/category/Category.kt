package com.commerce.coordination.category

import com.commerce.coordination.product.Products

data class Category(
    val id: Long,
    val name: String,
    val products: Products
)

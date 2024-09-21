package com.commerce.coordination.product

import com.commerce.coordination.category.Category

data class Product(
    override val price: Price, override val category: Category,
) : ProductProps

interface ProductProps {
    val price: Price
    val category: Category
}

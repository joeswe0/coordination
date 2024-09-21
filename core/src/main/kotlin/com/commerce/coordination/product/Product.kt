package com.commerce.coordination.product

import com.commerce.coordination.category.Category

data class Product(
    override val amount: Amount, override val category: Category,
) : ProductProps

interface ProductProps {
    val amount: Amount
    val category: Category
}

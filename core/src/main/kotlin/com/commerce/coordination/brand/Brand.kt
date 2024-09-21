package com.commerce.coordination.brand

import com.commerce.coordination.product.Products

data class Brand(
    val id: Long,
    override val name: String,
) : BrandProps

interface BrandProps {
    val name: String
}

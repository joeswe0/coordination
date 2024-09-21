package com.commerce.coordination.brand

interface BrandRepository {
    fun createBrand(name: String): Brand
}

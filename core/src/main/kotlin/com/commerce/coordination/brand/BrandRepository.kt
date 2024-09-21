package com.commerce.coordination.brand

interface BrandRepository {
    fun createBrand(name: String): Brand
    fun getBrand(id: Long): Brand?
    fun getAllBrands(): Brands
    fun updateBrandProducts(brand: Brand): Brand
}

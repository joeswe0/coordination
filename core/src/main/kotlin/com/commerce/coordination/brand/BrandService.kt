package com.commerce.coordination.brand

import org.springframework.stereotype.Service

@Service
class BrandService(val brandRepository: BrandRepository) {
    fun createBrand(name: String): Brand =
        brandRepository.createBrand(name)
}

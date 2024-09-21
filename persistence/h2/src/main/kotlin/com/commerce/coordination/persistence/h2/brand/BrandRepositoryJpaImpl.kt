package com.commerce.coordination.persistence.h2.brand

import com.commerce.coordination.brand.Brand
import com.commerce.coordination.brand.BrandRepository
import com.commerce.coordination.product.Products
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
internal class BrandRepositoryJpaImpl(private val brandJpaRepository: BrandJpaRepository) : BrandRepository {
    @Transactional
    override fun createBrand(name: String): Brand = brandJpaRepository.save(
        BrandEntity(
            name = name
        )
    ).let {
        Brand(
            id = it.id, name = it.name, products = Products(emptyList())
        )
    }
}

package com.commerce.coordination.persistence.h2.brand

import com.commerce.coordination.brand.Brand
import com.commerce.coordination.brand.BrandRepository
import com.commerce.coordination.brand.Brands
import com.commerce.coordination.persistence.h2.product.ProductEntity
import com.commerce.coordination.product.Products
import org.springframework.data.repository.findByIdOrNull
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

    @Transactional
    override fun getBrand(id: Long): Brand? {
        return brandJpaRepository.findByIdOrNull(id)?.let {
            mapToDomain(it)
        }
    }

    override fun getAllBrands(): Brands {
        return Brands(brandJpaRepository.findAll().map {
            mapToDomain(it)
        })
    }

    @Transactional
    override fun updateBrandProducts(brand: Brand): Brand {
        return brandJpaRepository.save(brand.let {
            BrandEntity(id = it.id, name = it.name)
        }.also { saved ->
            saved.clearProducts()
            saved.addProducts(brand.products.products.map {
                ProductEntity(
                    price = it.price.value, category = it.category, parent = saved
                )
            })
        }).let {
            mapToDomain(it)
        }
    }

    private fun mapToDomain(it: BrandEntity) = Brand(
        id = it.id, name = it.name, products = it.products()
    )
}

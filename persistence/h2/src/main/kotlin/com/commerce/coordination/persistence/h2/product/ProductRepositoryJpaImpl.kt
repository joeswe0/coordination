package com.commerce.coordination.persistence.h2.product

import com.commerce.coordination.product.ProductRepository
import org.springframework.stereotype.Repository

@Repository
internal class ProductRepositoryJpaImpl(private val productJpaRepository: ProductJpaRepository) :
    ProductRepository

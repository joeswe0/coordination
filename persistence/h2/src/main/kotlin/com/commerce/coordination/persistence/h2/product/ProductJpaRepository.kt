package com.commerce.coordination.persistence.h2.product

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal interface ProductJpaRepository : JpaRepository<ProductEntity, Long>

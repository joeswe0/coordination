package com.commerce.coordination.persistence.h2.brand

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
internal interface BrandJpaRepository : JpaRepository<BrandEntity, Long> {
    @Query("SELECT DISTINCT b FROM BrandEntity b LEFT JOIN FETCH b.mutableProductEntity")
    fun findAllWithProducts(): List<BrandEntity>
}

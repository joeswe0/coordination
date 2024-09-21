package com.commerce.coordination.persistence.h2.brand

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal interface BrandJpaRepository : JpaRepository<BrandEntity, Long>

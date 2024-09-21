package com.commerce.coordination.persistence.h2.brand

import com.commerce.coordination.persistence.h2.product.ProductEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(
    name = "brand",
)
internal class BrandEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column
    val name: String,
    ) {

    @OneToMany(
        mappedBy = "brand",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
    )
    private val mutableProductEntity: MutableList<ProductEntity> = mutableListOf()
}

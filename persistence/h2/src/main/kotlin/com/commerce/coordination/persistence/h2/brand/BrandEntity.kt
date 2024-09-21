package com.commerce.coordination.persistence.h2.brand

import com.commerce.coordination.persistence.h2.product.ProductEntity
import com.commerce.coordination.product.Amount
import com.commerce.coordination.product.Product
import com.commerce.coordination.product.Products
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
    fun clearProducts() {
        mutableProductEntity.clear()
    }

    fun addProduct(product: ProductEntity) {
        mutableProductEntity.add(product)
    }

    fun addProducts(products: List<ProductEntity>) {
        mutableProductEntity.addAll(products)
    }

    fun products(): Products = Products(
        mutableProductEntity.map {
            Product(
                amount = Amount(value = it.amount), category = it.category
            )
        }
    )
}

package com.commerce.coordination.product

data class Products(val products: Collection<Product>) {
    fun sumOfPrice() = products.sumOf { it.price.value }
}

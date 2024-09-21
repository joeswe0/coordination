package com.commerce.coordination.brand

import com.commerce.coordination.category.Category
import com.commerce.coordination.product.Price
import com.commerce.coordination.product.Product
import com.commerce.coordination.product.Products
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class BrandsTest {
    @Test
    fun `가장 저렴한 총 가격을 가진 브랜드를 반환한다`() {
        // Given
        val brandA = Brand(
            1, "Brand A", Products(
                listOf(
                    Product(Price(10000), Category.TOP),
                    Product(Price(20000), Category.PANTS)
                )
            )
        )
        val brandB = Brand(
            2, "Brand B", Products(
                listOf(
                    Product(Price(5000), Category.TOP),
                    Product(Price(10000), Category.PANTS)
                )
            )
        )

        val brands = Brands(listOf(brandA, brandB))

        // When
        val result = brands.lowestPriceBrandAndTotalPrice()

        // Then
        assertNotNull(result)
        assertEquals("Brand B", result!!.first.name)
        assertEquals(15000, result.second)
    }

    @Test
    fun `각 카테고리에서 가장 저렴한 가격을 가진 브랜드를 반환한다`() {
        // Given
        val brandA = Brand(
            1L, "Brand A", Products(
                listOf(
                    Product(Price(10000), Category.TOP),
                    Product(Price(20000), Category.PANTS)
                )
            )
        )
        val brandB = Brand(
            2L, "Brand B", Products(
                listOf(
                    Product(Price(5000), Category.TOP),
                    Product(Price(15000), Category.PANTS)
                )
            )
        )
        val brandC = Brand(
            3L, "Brand C", Products(
                listOf(
                    Product(Price(12000), Category.TOP),
                    Product(Price(9000), Category.PANTS)
                )
            )
        )

        val brands = Brands(listOf(brandA, brandB, brandC))

        // When
        val result = brands.lowestPricesByCategory()

        // Then
        assertEquals("Brand B", result[Category.TOP]!!.first.name)
        assertEquals(5000, result[Category.TOP]!!.second)

        assertEquals("Brand C", result[Category.PANTS]!!.first.name)
        assertEquals(9000, result[Category.PANTS]!!.second)
    }
}

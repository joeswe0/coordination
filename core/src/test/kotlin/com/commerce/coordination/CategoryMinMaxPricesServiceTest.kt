package com.commerce.coordination

import com.commerce.coordination.brand.Brand
import com.commerce.coordination.brand.BrandRepository
import com.commerce.coordination.brand.Brands
import com.commerce.coordination.category.Category
import com.commerce.coordination.product.Amount
import com.commerce.coordination.product.Product
import com.commerce.coordination.product.Products
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [TestApplication::class])
class CategoryMinMaxPricesServiceTest {

    @MockkBean
    private lateinit var brandRepository: BrandRepository

    @Autowired
    private lateinit var categoryMinMaxPricesService: CategoryMinMaxPricesService

    @Test
    fun `카테고리에서 가장 싸고 비싼 프로덕트를 가져온다`() {
        // Given
        val brands = listOf(
            Brand(
                id = 1L,
                name = "Brand A",
                products = Products(
                    listOf(
                        Product(Amount(10000), Category.TOP),
                        Product(Amount(5000), Category.OUTERWEAR),
                        Product(Amount(3000), Category.PANTS)
                    )
                )
            ),
            Brand(
                id = 2L,
                name = "Brand B",
                products = Products(
                    listOf(
                        Product(Amount(12000), Category.TOP),
                        Product(Amount(6000), Category.OUTERWEAR),
                        Product(Amount(3500), Category.PANTS)
                    )
                )
            ),
            Brand(
                id = 3L,
                name = "Brand C",
                products = Products(
                    listOf(
                        Product(Amount(8000), Category.TOP),
                        Product(Amount(4500), Category.OUTERWEAR),
                        Product(Amount(2500), Category.PANTS)
                    )
                )
            )
        )

        // When
        every { brandRepository.getAllBrands() } returns Brands(brands)

        // Then
        val result = categoryMinMaxPricesService.getCategoryMinMaxPrices(Category.TOP.name)

        // 예상되는 최저가와 최고가 검증
        assertEquals("Brand C", result.lowestPrice.brandName)
        assertEquals(8000, result.lowestPrice.price)

        assertEquals("Brand B", result.highestPrice.brandName)
        assertEquals(12000, result.highestPrice.price)
    }
}

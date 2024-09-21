package com.commerce.coordination

import com.commerce.coordination.brand.Brand
import com.commerce.coordination.brand.BrandRepository
import com.commerce.coordination.category.Category
import com.commerce.coordination.product.Amount
import com.commerce.coordination.product.Product
import com.commerce.coordination.product.Products
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [TestApplication::class])
class LowestPriceByCategoryServiceTest{
    @MockkBean
    private lateinit var brandRepository: BrandRepository

    @Autowired
    private lateinit var lowestPriceByCategoryService: LowestPriceByCategoryService

    @Test
    fun `가장 적은 금액의 브랜드 상품을 가져온다`() {
        // Given
        val brands = listOf(
            Brand(
                id = 1L,
                name = "A",
                products = Products(
                    listOf(
                        Product(Amount(12000), Category.TOP),
                        Product(Amount(2000), Category.BAG)
                    )
                )
            ),
            Brand(
                id = 2L,
                name = "B",
                products = Products(
                    listOf(
                        Product(Amount(10000), Category.TOP),
                        Product(Amount(3000), Category.BAG)
                    )
                )
            ),
            Brand(
                id = 3L,
                name = "C",
                products = Products(
                    listOf(
                        Product(Amount(15000), Category.TOP),
                        Product(Amount(2500), Category.BAG)
                    )
                )
            )
        )

        // brandRepository의 동작을 모킹
        every { brandRepository.getAllBrands() } returns brands

        // When: 서비스 메서드 호출
        val result = lowestPriceByCategoryService.getLowestPriceByCategory()

        // Then: 카테고리별 최저가 검증
        val expectedCategoryPrices = listOf(
            CategoryPrice(Category.TOP, "B", 10000),
            CategoryPrice(Category.BAG, "A", 2000)
        )
        assertEquals(expectedCategoryPrices, result.categoryPrices)
        assertEquals(12000, result.totalAmount) // 총액 검증
    }
}

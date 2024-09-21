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
class LowestPriceForSingleBrandServiceTest {

    @MockkBean
    private lateinit var brandRepository: BrandRepository

    @Autowired
    private lateinit var lowestPriceForSingleBrandService: LowestPriceForSingleBrandService

    @Test
    fun `should return lowest price for a single brand`() {
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
        val result = lowestPriceForSingleBrandService.getLowestPriceBySingleBrand()

        val expectedCategoryPrices = listOf(
            CategoryPrice(Category.TOP, "Brand C", 8000),
            CategoryPrice(Category.OUTERWEAR, "Brand C", 4500),
            CategoryPrice(Category.PANTS, "Brand C", 2500)
        )

        assertEquals("Brand C", result.brandName)
        assertEquals(expectedCategoryPrices, result.categoryPrices)
        assertEquals(15000, result.totalAmount)  // 총액 검증
    }
}

package com.commerce.coordination


import com.commerce.coordination.brand.Brand
import com.commerce.coordination.brand.BrandRepository
import com.commerce.coordination.brand.Brands
import com.commerce.coordination.category.Category
import com.commerce.coordination.product.Price
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
    fun `단일 브랜드 기준으로 전체 카테고리 최저가 브랜드를 가져온다`() {
        // Given
        val brands = listOf(
            Brand(
                id = 1L,
                name = "Brand A",
                products = Products(
                    listOf(
                        Product(Price(10000), Category.TOP),
                        Product(Price(5000), Category.OUTERWEAR),
                        Product(Price(3000), Category.PANTS)
                    )
                )
            ),
            Brand(
                id = 2L,
                name = "Brand B",
                products = Products(
                    listOf(
                        Product(Price(12000), Category.TOP),
                        Product(Price(6000), Category.OUTERWEAR),
                        Product(Price(3500), Category.PANTS)
                    )
                )
            ),
            Brand(
                id = 3L,
                name = "Brand C",
                products = Products(
                    listOf(
                        Product(Price(8000), Category.TOP),
                        Product(Price(4500), Category.OUTERWEAR),
                        Product(Price(2500), Category.PANTS)
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
        assertEquals(15000, result.totalPrice)  // 총액 검증
    }
}

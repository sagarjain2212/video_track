package com.youtubetrack

import app.cash.turbine.test
import com.youtubetrack.interfaces.ProductRepository
import com.youtubetrack.model.ProductEntity
import com.youtubetrack.usecases.GetProductsUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

// Location: app/src/test/java/com/youtubetrack/domain/use_case/GetProductsUseCaseTest.kt

class GetProductsUseCaseTest {

    private lateinit var getProductsUseCase: GetProductsUseCase
    private val repository: ProductRepository = mockk() // The "Fake" Repository

    @Before
    fun setUp() {
        getProductsUseCase = GetProductsUseCase(repository)
    }

    @Test
    fun `Get products, should return sorted products by title`() = runTest {
        // 1. GIVEN: We tell the mock repository what to return
        val unsortedProducts = listOf(
            ProductEntity(
                id = 1,
                title = "Zebra",
                price = 1.0,
                description = "",
                category = "",
                image = ""
            ),
            ProductEntity(id = 2, title = "Apple", price = 1.0, description = "", category = "", image = "")
        )

        // When the repository is called, return a Flow of this list
        every { repository.getProducts() } returns flowOf(unsortedProducts)

        // 2. WHEN: we execute the use case
        // We use Turbine to "pick up" the flow emissions
        getProductsUseCase().test {
            val result = awaitItem()

            // 3. THEN: The first item (Apple) should come before the second (Zebra)
            assert(result[0].title == "Apple")
            assert(result[1].title == "Zebra")
            awaitComplete()
        }
    }
}
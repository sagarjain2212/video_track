package com.youtubetrack

import app.cash.turbine.test
import com.youtubetrack.interfaces.ProductRepository
import com.youtubetrack.state.ValidationEvent
import com.youtubetrack.usecases.GetProductsUseCase
import com.youtubetrack.viewmodel.ProductViewModel
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

// app/src/test/java/com/youtubetrack/presentation/ProductViewModelTest.kt

class ProductViewModelTest {

    private lateinit var viewModel: ProductViewModel
    private val repository: ProductRepository = mockk(relaxed = true)

    // This rule allows Coroutines to run on the JVM for testing
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var getProductsUseCase: GetProductsUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher) // Redirect "Main" to our test dispatcher
        getProductsUseCase = GetProductsUseCase(repository)
        viewModel = ProductViewModel(getProductsUseCase, repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `When title is empty, should emit Error event and NOT save to Room`() = runTest {
        // 1. GIVEN: Invalid inputs
        val title = ""
        val category = "Test1"

        // 2. START LISTENING FIRST
        viewModel.validationEvents.test {

            // 3. WHEN: Now trigger the action
            viewModel.singleItemInsert(title, category)

            // 4. THEN: Await the item
            val event = awaitItem()
            assert(event is ValidationEvent.Error)
            assert((event as ValidationEvent.Error).message == "Title cannot be empty")

            // Ensure no other events were sent
            expectNoEvents()
        }

        // 5. VERIFY: Repository was never touched
        coVerify(exactly = 0) { repository.addProduct(any()) }
    }

    @Test
    fun `When inputs are valid, should save to Room and emit Success`() = runTest {
        // GIVEN
        val title = "New Mic"
        val category = "Test2"

        // WHEN
        viewModel.singleItemInsert(title, category)
        testDispatcher.scheduler.advanceUntilIdle()

        // THEN
        viewModel.validationEvents.test {
            assert(awaitItem() is ValidationEvent.Success)
        }

        // Verify repository.addProduct was called exactly 1 time
        coVerify(exactly = 1) { repository.addProduct(any()) }
    }
}
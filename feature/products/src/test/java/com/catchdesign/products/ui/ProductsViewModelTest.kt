package com.catchdesign.products.ui

import com.catchdesign.domain.model.APIState
import com.catchdesign.products.ui.repository.ProductsFakeRepository
import com.catchdesign.products.ui.repository.ProductsFakeRepositoryError
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class ProductsViewModelTest {
    private lateinit var viewModel: ProductsViewModel
    private val productsUseCase = ProductsFakeRepository()

    val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ProductsViewModel(
            productsUseCase = productsUseCase,
            coroutineDispatcher = testDispatcher
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getUiState initial value is empty`() = runTest {
        val state = viewModel.uiState.value
        assertEquals(false, state.isRefreshing)
        assertTrue(state.projectsAPIState is APIState.Idle)
    }

    @Test
    fun `handleAction OnRefresh updates isRefreshing then fetches products successfully`() =
        runTest {
            viewModel.handleAction(ProductsAction.OnRefresh)

            assertEquals(true, viewModel.uiState.value.isRefreshing)
            testScheduler.advanceUntilIdle()

            assertEquals(false, viewModel.uiState.value.isRefreshing)
            assertTrue(viewModel.uiState.value.projectsAPIState is APIState.Success)
        }

    @Test
    fun `fetchProducts handles error correctly`() = runTest {
        val viewModel = ProductsViewModel(
            productsUseCase = ProductsFakeRepositoryError(),
            coroutineDispatcher = testDispatcher
        )
        assertTrue(viewModel.uiState.value.projectsAPIState is APIState.Idle)
        assertEquals(false, viewModel.uiState.value.isRefreshing)

        viewModel.handleAction(ProductsAction.OnRefresh)

        assertEquals(true, viewModel.uiState.value.isRefreshing)
        testScheduler.advanceUntilIdle()
        assertEquals(false, viewModel.uiState.value.isRefreshing)
        assertTrue(viewModel.uiState.value.projectsAPIState is APIState.Error)

    }

}
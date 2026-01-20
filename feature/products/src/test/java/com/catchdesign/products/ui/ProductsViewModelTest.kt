package com.catchdesign.products.ui

import com.catchdesign.domain.model.APIState
import com.catchdesign.products.ui.usecase.ProductsFakeUseCase
import com.catchdesign.products.ui.usecase.ProductsFakeUseCaseError
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
    private val productsUseCase = ProductsFakeUseCase()

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
    fun `uiState initial value is correct`() = runTest {
        val state = viewModel.uiState.value
        assertEquals(true, state.showBackground)
        assertEquals(false, state.isRefreshing)
        assertTrue(state.projectsAPIState is APIState.Idle)
    }

    @Test
    fun `handleAction OnRefresh and fetchProducts successfully`() =
        runTest {
            viewModel.handleAction(ProductsAction.OnRefresh)
            assertEquals(true, viewModel.uiState.value.showBackground)
            assertEquals(true, viewModel.uiState.value.isRefreshing)
            testScheduler.advanceUntilIdle()

            assertEquals(false, viewModel.uiState.value.isRefreshing)
            assertEquals(false, viewModel.uiState.value.showBackground)
            assertTrue(viewModel.uiState.value.projectsAPIState is APIState.Success)
        }

    @Test
    fun `handleAction OnRefresh and fetchProducts error`() = runTest {
        val viewModel = ProductsViewModel(
            productsUseCase = ProductsFakeUseCaseError(),
            coroutineDispatcher = testDispatcher
        )
        assertTrue(viewModel.uiState.value.projectsAPIState is APIState.Idle)
        assertEquals(true, viewModel.uiState.value.showBackground)
        assertEquals(false, viewModel.uiState.value.isRefreshing)

        viewModel.handleAction(ProductsAction.OnRefresh)

        assertEquals(true, viewModel.uiState.value.isRefreshing)
        testScheduler.advanceUntilIdle()
        assertEquals(false, viewModel.uiState.value.isRefreshing)
        assertEquals(true, viewModel.uiState.value.showBackground)
        assertTrue(viewModel.uiState.value.projectsAPIState is APIState.Error)

    }

}
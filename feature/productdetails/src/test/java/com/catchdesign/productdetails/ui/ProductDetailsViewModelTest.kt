package com.catchdesign.productdetails.ui

import com.catchdesign.domain.model.APIState
import com.catchdesign.productdetails.usecase.ProductDetailsFakeUseCase
import com.catchdesign.productdetails.usecase.ProductDetailsFakeUseCaseError
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

@OptIn(ExperimentalCoroutinesApi::class)
class ProductDetailsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetch products successfully`() = runTest {
        val viewModel = ProductDetailsViewModel(
            productDetailsUseCase = ProductDetailsFakeUseCase(),
            coroutineDispatcher = testDispatcher
        )
        assertTrue(viewModel.uiState.value.productDetailsAPIState is APIState.Idle)
        testScheduler.advanceUntilIdle()
        assertTrue(viewModel.uiState.value.productDetailsAPIState is APIState.Success)
    }

    @Test
    fun `fetch products error`() = runTest {
        val viewModel = ProductDetailsViewModel(
            productDetailsUseCase = ProductDetailsFakeUseCaseError(),
            coroutineDispatcher = testDispatcher
        )
        assertTrue(viewModel.uiState.value.productDetailsAPIState is APIState.Idle)
        testScheduler.advanceUntilIdle()
        assertTrue(viewModel.uiState.value.productDetailsAPIState is APIState.Error)
    }

}
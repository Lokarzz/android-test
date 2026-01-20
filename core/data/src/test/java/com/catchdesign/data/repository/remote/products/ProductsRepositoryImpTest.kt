package com.catchdesign.data.repository.remote.products

import com.catchdesign.data.repository.remote.products.apiservice.FailFakeApiService
import com.catchdesign.data.repository.remote.products.apiservice.SuccessFakeApiService
import com.catchdesign.data.util.Result
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ProductsRepositoryImpTest {


    @Test
    fun `fetch products successfully`() = runTest {
        val fakeApiService = SuccessFakeApiService()
        val productsRepository = ProductsRepositoryImp(fakeApiService)

        val result = productsRepository.getProducts()
        assertTrue(result is Result.Success)
        assert((result as Result.Success).value == fakeApiService.fakeProducts)
    }

    @Test
    fun `fetch products unsuccessfully`() = runTest {
        val fakeApiService = FailFakeApiService()
        val productsRepository = ProductsRepositoryImp(fakeApiService)

        val result = productsRepository.getProducts()
        assertTrue(result is Result.Failure)
        assert((result as Result.Failure).error.message == fakeApiService.errorMessage)
    }

    @Test
    fun `fetch product details successfully`() = runTest {
        val fakeApiService = SuccessFakeApiService()
        val productsRepository = ProductsRepositoryImp(fakeApiService)

        val result = productsRepository.getProductDetails(1)
        assertTrue(result is Result.Success)
        assert((result as Result.Success).value == fakeApiService.fakeProductDetails)
    }

    @Test
    fun `fetch product details unsuccessfully due to missing product`() = runTest {
        val fakeApiService = SuccessFakeApiService()
        val productsRepository = ProductsRepositoryImp(fakeApiService)

        val result = productsRepository.getProductDetails(2)
        assertTrue(result is Result.Failure)
        assertTrue((result as Result.Failure).error.message!!.isNotEmpty())
    }

    @Test
    fun `fetch product details unsuccessfully`() = runTest {
        val fakeApiService = FailFakeApiService()
        val productsRepository = ProductsRepositoryImp(fakeApiService)

        val result = productsRepository.getProductDetails(1)
        assertTrue(result is Result.Failure)
        assert((result as Result.Failure).error.message == fakeApiService.errorMessage)
    }
}
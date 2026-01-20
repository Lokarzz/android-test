package com.catchdesign.domain.usecase.products

import com.catchdesign.domain.model.APIState
import com.catchdesign.domain.usecase.productdetails.ProductDetailsUseCaseImp
import com.catchdesign.domain.usecase.products.repository.FailFakeRepository
import com.catchdesign.domain.usecase.products.repository.SuccessFakeRepository
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ProductsUseCaseImpTest {


    @Test
    fun `fetch products successfully`() = runTest {
        val repository = SuccessFakeRepository()
        val useCase = ProductsUseCaseImp(repository)


        assertTrue(useCase().first() is APIState.Loading)

        val apiState = useCase().drop(1).first()
        assertTrue(apiState is APIState.Success)
        assertTrue((apiState as APIState.Success).data.isNotEmpty())
    }

    @Test
    fun `fetch products unsuccessfully`() = runTest {
        val repository = FailFakeRepository()
        val useCase = ProductsUseCaseImp(repository)


        assertTrue(useCase().first() is APIState.Loading)

        val apiState = useCase().drop(1).first()
        assertTrue(apiState is APIState.Error)
        assertTrue((apiState as APIState.Error).apiError.message == repository.errorMessage)
    }

    @Test
    fun `fetch product details successfully`() = runTest {
        val repository = SuccessFakeRepository()
        val useCase = ProductDetailsUseCaseImp(repository)

        assertTrue(useCase(1).first() is APIState.Loading)


        val apiState = useCase(1).drop(1).first()
        assertTrue(apiState is APIState.Success)
        assertTrue((apiState as APIState.Success).data.id == 1)
    }

    @Test
    fun `fetch product details unsuccessfully`() = runTest {
        val repository = FailFakeRepository()
        val useCase = ProductDetailsUseCaseImp(repository)


        assertTrue(useCase(1).first() is APIState.Loading)

        val apiState = useCase(1).drop(1).first()
        assertTrue(apiState is APIState.Error)
        assertTrue((apiState as APIState.Error).apiError.message == repository.errorMessage)
    }

}
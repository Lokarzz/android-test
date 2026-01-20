package com.catchdesign.domain.usecase.productdetails

import com.catchdesign.data.repository.remote.products.ProductsRepository
import com.catchdesign.data.util.Result
import com.catchdesign.domain.model.APIState
import com.catchdesign.domain.model.productdetails.ProductDetailsUI
import com.catchdesign.domain.model.productdetails.toUI
import com.catchdesign.domain.util.flow.applyDefaultState
import com.catchdesign.domain.util.throwable.handleError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


internal class ProductDetailsUseCaseImp @Inject constructor(private val productsRepository: ProductsRepository) :
    ProductDetailsUseCase {
    override suspend fun invoke(id: Int): Flow<APIState<ProductDetailsUI>> {
        return flow {
            emit(
                when (val result = productsRepository.getProductDetails(id)) {
                    is Result.Failure -> APIState.Error(apiError = result.error.handleError())
                    is Result.Success -> APIState.Success(data = result.value.toUI())
                }
            )
        }.applyDefaultState()
    }
}
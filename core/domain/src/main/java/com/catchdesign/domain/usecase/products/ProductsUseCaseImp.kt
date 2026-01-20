package com.catchdesign.domain.usecase.products

import com.catchdesign.data.repository.remote.products.ProductsRepository
import com.catchdesign.data.util.Result
import com.catchdesign.domain.model.APIState
import com.catchdesign.domain.model.products.ProductsUI
import com.catchdesign.domain.model.products.toUI
import com.catchdesign.domain.util.flow.applyDefaultState
import com.catchdesign.domain.util.throwable.handleError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


internal class ProductsUseCaseImp @Inject constructor(private val productsRepository: ProductsRepository) :
    ProductsUseCase {
    override suspend fun invoke(): Flow<APIState<ProductsUI>> {
        return flow {
            emit(
                when (val result = productsRepository.getProducts()) {
                    is Result.Failure -> APIState.Error(apiError = result.error.handleError())
                    is Result.Success -> APIState.Success(data = result.value.toUI())
                }
            )
        }.applyDefaultState()
    }
}
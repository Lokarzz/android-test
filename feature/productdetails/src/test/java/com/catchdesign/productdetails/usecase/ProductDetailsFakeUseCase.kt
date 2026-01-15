package com.catchdesign.productdetails.usecase

import com.catchdesign.domain.model.APIError
import com.catchdesign.domain.model.APIState
import com.catchdesign.domain.model.productdetails.ProductDetailsUI
import com.catchdesign.domain.usecase.productdetails.ProductDetailsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class ProductDetailsFakeUseCase : ProductDetailsUseCase {
    override suspend fun invoke(): Flow<APIState<ProductDetailsUI>> {
        return flow {
            emit(
                APIState.Success(
                    ProductDetailsUI(
                        id = 1,
                        name = "Product 1",
                        description = "Description 1"
                    )
                )
            )
        }
    }
}

internal class ProductDetailsFakeUseCaseError : ProductDetailsUseCase {
    override suspend fun invoke(): Flow<APIState<ProductDetailsUI>> {
        return flow {
            emit(
                APIState.Error(
                    APIError(
                        key = "not_found",
                        message = "Not found",
                        code = 404
                    )
                )
            )
        }
    }

}
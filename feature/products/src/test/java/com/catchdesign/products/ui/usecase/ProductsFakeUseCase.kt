package com.catchdesign.products.ui.usecase

import com.catchdesign.domain.model.APIError
import com.catchdesign.domain.model.APIState
import com.catchdesign.domain.model.products.ProductUI
import com.catchdesign.domain.model.products.ProductsUI
import com.catchdesign.domain.usecase.products.ProductsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


internal class ProductsFakeUseCase : ProductsUseCase {
    override suspend fun invoke(): Flow<APIState<ProductsUI>> {
        return flow {
            emit(
                APIState.Success(
                    listOf(
                        ProductUI(id = 1, "Product 1", "Tag 1"),
                        ProductUI(id = 2, "Product 2", "Tag 2")
                    )
                )
            )
        }
    }
}

internal class ProductsFakeUseCaseError : ProductsUseCase {
    override suspend fun invoke(): Flow<APIState<ProductsUI>> {
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
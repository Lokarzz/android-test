package com.catchdesign.domain.usecase.products

import com.catchdesign.domain.model.APIState
import com.catchdesign.domain.model.products.ProductUI
import com.catchdesign.domain.model.products.ProductsUI
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductsUseCaseImp : ProductsUseCase {
    override suspend fun invoke(): Flow<APIState<ProductsUI>> {
        return flow {
            // add sample delay
            delay(300)
            emit(
                APIState.Success(
                    data = (1..50).map {
                        ProductUI(
                            id = it,
                            name = "Name $it",
                            tagLine = "Tag Line $it"
                        )
                    }
                )
            )
        }
    }
}
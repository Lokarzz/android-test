package com.catchdesign.domain.usecase.products

import com.catchdesign.domain.model.APIState
import com.catchdesign.domain.model.products.ProductsUI
import kotlinx.coroutines.flow.Flow

interface ProductsUseCase {

    suspend operator fun invoke(): Flow<APIState<ProductsUI>>


}
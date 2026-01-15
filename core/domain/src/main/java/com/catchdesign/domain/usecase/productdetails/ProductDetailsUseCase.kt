package com.catchdesign.domain.usecase.productdetails

import com.catchdesign.domain.model.APIState
import com.catchdesign.domain.model.productdetails.ProductDetailsUI
import kotlinx.coroutines.flow.Flow

interface ProductDetailsUseCase {

    suspend operator fun invoke(): Flow<APIState<ProductDetailsUI>>


}
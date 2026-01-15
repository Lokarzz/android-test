package com.catchdesign.productdetails.ui

import com.catchdesign.domain.model.APIState
import com.catchdesign.domain.model.productdetails.ProductDetailsUI

data class ProductDetailsUIState(
    val productDetailsAPIState: APIState<ProductDetailsUI> = APIState.Idle,
)
package com.catchdesign.products.ui

import com.catchdesign.domain.model.APIState
import com.catchdesign.domain.model.products.ProductsUI


data class ProductsUIState(
    val projectsAPIState: APIState<ProductsUI> = APIState.Idle,
    val isRefreshing: Boolean = false,
    val showBackground: Boolean = true,
)



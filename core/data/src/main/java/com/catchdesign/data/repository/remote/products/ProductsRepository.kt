package com.catchdesign.data.repository.remote.products

import com.catchdesign.data.model.remote.product.list.ProductsResponse
import com.catchdesign.data.util.Result

interface ProductsRepository {

    suspend fun getProducts(): Result<ProductsResponse>

}
package com.catchdesign.data.repository.remote.products

import com.catchdesign.data.model.remote.product.detail.ProductDetails
import com.catchdesign.data.model.remote.product.list.ProductsResponse
import com.catchdesign.data.util.Result

interface ProductsRepository {

    suspend fun getProducts(): Result<ProductsResponse>

    suspend fun getProductDetails(id: Int): Result<ProductDetails>

}
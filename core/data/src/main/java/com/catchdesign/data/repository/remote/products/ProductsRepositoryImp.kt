package com.catchdesign.data.repository.remote.products

import com.catchdesign.data.model.remote.product.list.ProductsResponse
import com.catchdesign.data.repository.remote.APIService
import com.catchdesign.data.util.Result
import com.catchdesign.data.util.safeSuspendRun
import javax.inject.Inject

class ProductsRepositoryImp @Inject constructor(private val apiService: APIService) :
    ProductsRepository {

    override suspend fun getProducts(): Result<ProductsResponse> {
        return safeSuspendRun {
            apiService.fetchProducts()
        }
    }
}
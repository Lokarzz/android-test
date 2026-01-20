package com.catchdesign.data.repository.remote.products.apiservice

import com.catchdesign.data.model.remote.product.detail.ProductDetails
import com.catchdesign.data.model.remote.product.detail.ProductDetailsResponse
import com.catchdesign.data.model.remote.product.list.Product
import com.catchdesign.data.model.remote.product.list.ProductsResponse
import com.catchdesign.data.repository.remote.APIService


internal class SuccessFakeApiService : APIService {
    val fakeProducts = (1..20).map {
        Product(
            id = it,
            title = "Subtitle $it",
            subtitle = "Product $it"
        )
    }

    val fakeProductDetails = ProductDetails(
        id = 1,
        title = "title 1",
        content = "content 1"
    )

    override suspend fun fetchProducts(): ProductsResponse {
        return fakeProducts
    }

    override suspend fun fetchProductDetails(): ProductDetailsResponse {
        return listOf(fakeProductDetails)
    }

}

internal class FailFakeApiService : APIService {
    val errorMessage = "error_message"

    override suspend fun fetchProducts(): ProductsResponse {
        throw Exception(errorMessage)
    }

    override suspend fun fetchProductDetails(): ProductDetailsResponse {
        throw Exception(errorMessage)
    }

}
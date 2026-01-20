package com.catchdesign.data.repository.remote

import com.catchdesign.data.model.remote.product.detail.ProductDetailsResponse
import com.catchdesign.data.model.remote.product.list.ProductsResponse
import retrofit2.http.GET


interface APIService {

    @GET("catchnz/android-test/master/data/data.json")
    suspend fun fetchProducts(): ProductsResponse

    /**
     * NOTE:
     * This endpoint is intended to represent a "Product Details" API.
     * Getting the details logic will be on repository layer.
     */
    @GET("catchnz/android-test/master/data/data.json")
    suspend fun fetchProductDetails(): ProductDetailsResponse

}
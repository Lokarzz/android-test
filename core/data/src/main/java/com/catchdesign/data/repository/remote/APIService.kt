package com.catchdesign.data.repository.remote

import com.catchdesign.data.model.remote.product.list.ProductsResponse
import retrofit2.http.GET


interface APIService {

    @GET("catchnz/android-test/master/data/data.json")
    suspend fun fetchProducts(): ProductsResponse


}
package com.catchdesign.domain.usecase.products.repository

import com.catchdesign.data.model.remote.product.detail.ProductDetails
import com.catchdesign.data.model.remote.product.list.Product
import com.catchdesign.data.model.remote.product.list.ProductsResponse
import com.catchdesign.data.repository.remote.products.ProductsRepository
import com.catchdesign.data.util.Result


internal class SuccessFakeRepository : ProductsRepository {
    override suspend fun getProducts(): Result<ProductsResponse> {
        val products = (1..20).map {
            Product(
                id = it,
                title = "Subtitle $it",
                subtitle = "Product $it"
            )
        }
        return Result.Success(products)
    }

    override suspend fun getProductDetails(id: Int): Result<ProductDetails> {
        return Result.Success(
            ProductDetails(
                id = 1,
                title = "title 1",
                content = "content 1"
            )
        )
    }
}

internal class FailFakeRepository : ProductsRepository {
    val errorMessage = "error_message"
    override suspend fun getProducts(): Result<ProductsResponse> {

        return Result.Failure(error = Throwable(errorMessage))
    }

    override suspend fun getProductDetails(id: Int): Result<ProductDetails> {
        return Result.Failure(error = Throwable(errorMessage))
    }
}

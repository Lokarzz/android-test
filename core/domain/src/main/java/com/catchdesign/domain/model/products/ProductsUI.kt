package com.catchdesign.domain.model.products

import com.catchdesign.data.model.remote.product.list.ProductsResponse


typealias ProductsUI = List<ProductUI>

data class ProductUI(
    val id: Int,
    val name: String,
    val tagLine: String
)

fun ProductsResponse.toUI(): ProductsUI {
    return map {
        ProductUI(
            id = it.id,
            name = it.title,
            tagLine = it.subtitle
        )
    }
}

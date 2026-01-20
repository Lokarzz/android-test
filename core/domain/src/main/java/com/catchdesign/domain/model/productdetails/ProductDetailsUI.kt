package com.catchdesign.domain.model.productdetails

import com.catchdesign.data.model.remote.product.detail.ProductDetails


data class ProductDetailsUI(
    val id: Int,
    val name: String,
    val description: String
)

fun ProductDetails.toUI(): ProductDetailsUI {
    return ProductDetailsUI(
        id = id,
        name = title,
        description = content
    )
}



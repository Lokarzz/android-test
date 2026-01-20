package com.catchdesign.data.model.remote.product.detail

import kotlinx.serialization.Serializable

@Serializable
data class ProductDetails(
    val id: Int,
    val title: String,
    val content: String
)
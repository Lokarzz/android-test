package com.catchdesign.data.model.remote.product.list

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int,
    val subtitle: String,
    val title: String
)
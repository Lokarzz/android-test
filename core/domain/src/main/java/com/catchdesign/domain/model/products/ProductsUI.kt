package com.catchdesign.domain.model.products


typealias ProductsUI = List<ProductUI>

data class ProductUI(
    val id: Int,
    val name: String,
    val tagLine: String
)

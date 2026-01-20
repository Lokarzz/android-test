package com.catchdesign.products.ui

interface ProductsEffect {
    data class OnError(val message: String) : ProductsEffect
}
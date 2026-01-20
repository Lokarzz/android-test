package com.catchdesign.productdetails.ui


internal interface ProductDetailsEffect {
    data class OnError(val message: String) : ProductDetailsEffect
}

internal interface ProductDetailsAction {
}
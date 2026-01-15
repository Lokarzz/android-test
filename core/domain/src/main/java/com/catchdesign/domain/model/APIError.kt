package com.catchdesign.domain.model

data class APIError(
    val key: String,
    val message: String,
    val code: Int
)
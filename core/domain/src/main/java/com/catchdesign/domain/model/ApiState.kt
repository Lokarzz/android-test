package com.catchdesign.domain.model

sealed interface APIState<out T> {
    object Idle : APIState<Nothing>
    object Loading : APIState<Nothing>
    data class Error(val apiError: APIError) : APIState<Nothing>
    data class Success<T>(val data: T) : APIState<T>
}
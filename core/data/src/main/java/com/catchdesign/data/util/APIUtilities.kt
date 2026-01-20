package com.catchdesign.data.util

import kotlinx.coroutines.CancellationException


internal suspend fun <T> safeSuspendRun(block: suspend () -> T): Result<T> {
    return try {
        Result.Success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.Failure(e)
    }
}


sealed interface Result<out T> {
    data class Success<T>(val value: T) : Result<T>
    data class Failure(val error: Throwable) : Result<Nothing>
}
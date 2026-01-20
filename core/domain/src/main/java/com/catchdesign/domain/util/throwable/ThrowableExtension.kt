package com.catchdesign.domain.util.throwable

import com.catchdesign.domain.model.APIError
import com.catchdesign.domain.model.HttpError
import com.catchdesign.domain.util.api.APIConstants
import com.google.gson.Gson
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException

internal fun Throwable.handleError(): APIError {
    return when (this) {
        is HttpException -> {
            return fetchAPIError()
        }

        is CancellationException -> {
            throw this
        }

        else -> {
            APIError(
                message = message ?: APIConstants.UnknownError.MESSAGE,
                key = APIConstants.UnknownError.KEY,
                code = 0
            )
        }
    }
}

internal fun HttpException.fetchAPIError(): APIError {
    val errorBodyString =
        response()?.errorBody()?.string() ?: APIConstants.UnknownError.MESSAGE
    val apiError = errorBodyString.fetchHttpError()
    return APIError(
        message = apiError?.message ?: errorBodyString,
        key = apiError?.key ?: APIConstants.UnknownError.KEY,
        code = code()
    )
}

internal fun String.fetchHttpError(): HttpError? {
    return try {
        Gson().fromJson(this, HttpError::class.java)
    } catch (_: Exception) {
        null
    }
}
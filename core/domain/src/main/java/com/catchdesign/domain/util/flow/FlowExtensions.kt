package com.catchdesign.domain.util.flow

import com.catchdesign.domain.model.APIState
import com.catchdesign.domain.util.throwable.handleError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart




internal fun <T> Flow<APIState<T>>.applyDefaultState(): Flow<APIState<T>> {
    return onStart { emit(APIState.Loading) }.catch { emit(APIState.Error(it.handleError())) }
}
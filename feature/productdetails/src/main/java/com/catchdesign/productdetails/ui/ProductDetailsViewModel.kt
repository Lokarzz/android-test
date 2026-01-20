package com.catchdesign.productdetails.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchdesign.di.coroutines.DefaultDispatcher
import com.catchdesign.domain.usecase.productdetails.ProductDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ProductDetailsViewModel @Inject constructor(
    private val productDetailsUseCase: ProductDetailsUseCase,
    @param:DefaultDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailsUIState())
    val uiState = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<ProductDetailsEffect>()
    val effects = _effects.asSharedFlow()


    init {
        val id = savedStateHandle.get<Int>("id")
        if (id == null) initProductIDError()
        fetchProductDetails(id = savedStateHandle.get<Int>("id") ?: 0)
    }

    private fun fetchProductDetails(id: Int) {
        viewModelScope.launch(coroutineDispatcher) {
            productDetailsUseCase(id).collect { apiState ->
                _uiState.update { it.copy(productDetailsAPIState = apiState) }
            }
        }
    }

    private fun initProductIDError() {
        viewModelScope.launch {
            _effects.emit(ProductDetailsEffect.OnError("Invalid product id"))
        }
    }

}
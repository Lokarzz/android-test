package com.catchdesign.productdetails.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchdesign.domain.usecase.productdetails.ProductDetailsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ProductDetailsViewModel(
    private val productDetailsUseCase: ProductDetailsUseCase,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailsUIState())
    val uiState = _uiState.asStateFlow()


    init {
        fetchProductDetails()
    }

    private fun fetchProductDetails() {
        viewModelScope.launch(coroutineDispatcher) {
            productDetailsUseCase().collect { apiState ->
                _uiState.update { it.copy(productDetailsAPIState = apiState) }
            }
        }
    }

}
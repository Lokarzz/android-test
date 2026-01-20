package com.catchdesign.productdetails.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchdesign.di.coroutines.DefaultDispatcher
import com.catchdesign.domain.usecase.productdetails.ProductDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ProductDetailsViewModel @Inject constructor(
    private val productDetailsUseCase: ProductDetailsUseCase,
    @param:DefaultDispatcher private val coroutineDispatcher: CoroutineDispatcher
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
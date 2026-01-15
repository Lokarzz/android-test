package com.catchdesign.products.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchdesign.domain.model.APIState
import com.catchdesign.domain.usecase.products.ProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ProductsViewModel(
    private val productsUseCase: ProductsUseCase
) : ViewModel() {


    private val _uiState = MutableStateFlow(ProductsUIState())
    val uiState = _uiState.asStateFlow()


    fun handleAction(action: ProductsAction) {
        when (action) {
            ProductsAction.OnRefresh -> {
                _uiState.update {
                    it.copy(
                        isRefreshing = true
                    )
                }
                fetchProducts()
            }
        }
    }

    fun fetchProducts() {
        viewModelScope.launch {
            productsUseCase().collect { apiState ->
                _uiState.update {
                    it.copy(
                        projectsAPIState = apiState,
                        isRefreshing = when (apiState) {
                            is APIState.Success,
                            is APIState.Error -> false
                            else -> it.isRefreshing
                        }
                    )
                }
            }
        }
    }
}
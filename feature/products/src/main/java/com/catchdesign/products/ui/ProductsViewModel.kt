package com.catchdesign.products.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchdesign.domain.model.APIState
import com.catchdesign.domain.usecase.products.ProductsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ProductsViewModel(
    private val productsUseCase: ProductsUseCase,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductsUIState())
    val uiState = _uiState.asStateFlow()

    fun handleAction(action: ProductsAction) {
        when (action) {
            ProductsAction.OnRefresh -> {
                if (uiState.value.isRefreshing) return
                _uiState.update {
                    it.copy(
                        isRefreshing = true
                    )
                }
                fetchProducts()
            }
        }
    }

    private fun fetchProducts() {
        viewModelScope.launch(coroutineDispatcher) {
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
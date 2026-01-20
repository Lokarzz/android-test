package com.catchdesign.products.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchdesign.di.coroutines.DefaultDispatcher
import com.catchdesign.domain.model.APIState
import com.catchdesign.domain.usecase.products.ProductsUseCase
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
internal class ProductsViewModel @Inject constructor(
    private val productsUseCase: ProductsUseCase,
    @param:DefaultDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductsUIState())
    val uiState = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<ProductsEffect>()
    val effects = _effects.asSharedFlow()

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
                when (apiState) {
                    is APIState.Error -> {
                        _effects.emit(ProductsEffect.OnError(apiState.apiError.message))
                    }
                    else -> {}
                }
                _uiState.update {
                    it.copy(
                        projectsAPIState = apiState,
                        isRefreshing = apiState is APIState.Loading,
                        showBackground = it.showBackground.takeIf { apiState !is APIState.Success }
                            ?: false
                    )
                }

            }
        }
    }
}
package com.catchdesign.products.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.catchdesign.domain.model.APIState
import com.catchdesign.domain.model.products.ProductsUI
import com.catchdesign.domain.usecase.products.ProductsUseCaseImp
import com.catchdesign.products.composables.ProductItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    modifier: Modifier = Modifier,
    productsViewModel: ProductsViewModel
) {

    val uiState by productsViewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = modifier.fillMaxSize()) {
        PullToRefreshBox(
            isRefreshing = uiState.isRefreshing,
            onRefresh = {
                productsViewModel.handleAction(action = ProductsAction.OnRefresh)
            }
        ) {
            LazyColumn(modifier = Modifier) {
                productsAPIState(uiState.projectsAPIState)
            }
        }
    }
}

private fun LazyListScope.productsAPIState(
    projectsAPIState: APIState<ProductsUI>
) {
    when (projectsAPIState) {
        is APIState.Error -> {}
        APIState.Idle -> {}
        APIState.Loading -> {}
        is APIState.Success -> {
            items(projectsAPIState.data) { productUI ->
                ProductItem(
                    modifier = Modifier
                        .fillMaxWidth(),
                    productUI = productUI
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    val productsViewModel = viewModel {
        ProductsViewModel(productsUseCase = ProductsUseCaseImp())
    }
    productsViewModel.handleAction(action = ProductsAction.OnRefresh)
    MaterialTheme {
        ProductsScreen(modifier = Modifier.fillMaxSize(), productsViewModel = productsViewModel)
    }
}
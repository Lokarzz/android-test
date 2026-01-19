package com.catchdesign.products.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.catchdesign.common.composables.BackgroundScreen
import com.catchdesign.domain.model.APIState
import com.catchdesign.domain.model.products.ProductsUI
import com.catchdesign.domain.usecase.products.ProductsUseCaseImp
import com.catchdesign.products.composables.ProductItem
import kotlinx.serialization.Serializable

@Serializable
object Products

fun NavGraphBuilder.productsScreen(
    onItemPress: (Int) -> Unit
) {
    composable<Products> {
        ProductsScreen(modifier = Modifier, onItemPress = onItemPress)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProductsScreen(
    modifier: Modifier = Modifier,
    productsViewModel: ProductsViewModel = viewModel { ProductsViewModel(productsUseCase = ProductsUseCaseImp()) },
    onItemPress: (Int) -> Unit
) {

    val uiState by productsViewModel.uiState.collectAsStateWithLifecycle()
    val state = rememberPullToRefreshState()

    Scaffold(modifier = modifier) {
        PullToRefreshBox(
            modifier = Modifier,
            isRefreshing = uiState.isRefreshing,
            onRefresh = {
                productsViewModel.handleAction(action = ProductsAction.OnRefresh)
            },
            state = state,
        ) {
            Box(modifier = Modifier) {
                BackgroundScreen(
                    modifier = Modifier
                        .fillMaxSize(),
                    show = uiState.projectsAPIState !is APIState.Success
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = it
                ) {
                    productsAPIState(uiState.projectsAPIState, onItemPress = onItemPress)
                }
            }
        }
    }

}

private fun LazyListScope.productsAPIState(
    projectsAPIState: APIState<ProductsUI>,
    onItemPress: (Int) -> Unit
) {
    when (projectsAPIState) {
        is APIState.Error -> {}
        APIState.Idle -> {}
        APIState.Loading -> {}
        is APIState.Success -> {
            items(projectsAPIState.data) { productUI ->
                ProductItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = { onItemPress(productUI.id) }),
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
        ProductsScreen(
            modifier = Modifier.fillMaxSize(),
            productsViewModel = productsViewModel,
            onItemPress = {})
    }
}
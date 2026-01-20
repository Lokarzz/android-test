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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.catchdesign.common.composables.BackgroundScreen
import com.catchdesign.domain.model.APIState
import com.catchdesign.domain.model.products.ProductUI
import com.catchdesign.domain.model.products.ProductsUI
import com.catchdesign.domain.usecase.products.ProductsUseCase
import com.catchdesign.products.composables.ProductItem
import com.catchdesign.util.context.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flow
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
    productsViewModel: ProductsViewModel = hiltViewModel(),
    onItemPress: (Int) -> Unit
) {

    val uiState by productsViewModel.uiState.collectAsStateWithLifecycle()
    val state = rememberPullToRefreshState()

    ProductsLaunchEffect(sharedFlow = productsViewModel.effects)
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
                    show = uiState.showBackground
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

@Composable
fun ProductsLaunchEffect(sharedFlow: SharedFlow<ProductsEffect>) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        sharedFlow.collect { productsEffect ->
            when (productsEffect) {
                is ProductsEffect.OnError -> {
                    context.showToast(message = productsEffect.message)
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
        ProductsViewModel(productsUseCase = object : ProductsUseCase {
            override suspend fun invoke(): Flow<APIState<ProductsUI>> {
                return flow {
                    emit(APIState.Success((1..20).map {
                        ProductUI(
                            id = it,
                            name = "Name $it",
                            tagLine = "Tagline $it"
                        )
                    }))
                }
            }

        }, coroutineDispatcher = Dispatchers.Default)
    }
    productsViewModel.handleAction(action = ProductsAction.OnRefresh)
    MaterialTheme {
        ProductsScreen(
            modifier = Modifier.fillMaxSize(),
            productsViewModel = productsViewModel,
            onItemPress = {})
    }
}
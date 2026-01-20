package com.catchdesign.productdetails.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.catchdesign.common.composables.CenterAlignedTopAppBar
import com.catchdesign.domain.model.APIState
import com.catchdesign.domain.model.productdetails.ProductDetailsUI
import com.catchdesign.domain.usecase.productdetails.ProductDetailsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.Serializable

@Serializable
data class ProductDetails(val id: Int)

fun NavController.toProductDetailsScreen(id: Int) {
    navigate(ProductDetails(id = id))
}

fun NavGraphBuilder.productDetailsScreen(modifier: Modifier = Modifier, onBackPress: () -> Unit) {
    composable<ProductDetails> {
        ProductDetailsScreen(modifier = modifier, onBackPress = onBackPress)
    }
}

@Composable
internal fun ProductDetailsScreen(
    modifier: Modifier = Modifier,
    productDetailsViewModel: ProductDetailsViewModel = hiltViewModel(),
    onBackPress: () -> Unit
) {

    val uiState by productDetailsViewModel.uiState.collectAsStateWithLifecycle()
    val name by remember(uiState) {
        derivedStateOf {
            (uiState.productDetailsAPIState as? APIState.Success)?.data?.name ?: ""
        }
    }

    Scaffold(
        modifier = modifier, topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier,
                name = name,
                onBackPress = onBackPress
            )
        }) { paddingValues ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            when (val apiState = uiState.productDetailsAPIState) {
                is APIState.Error -> {}
                APIState.Idle -> {}
                APIState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(16.dp)
                            .size(48.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }

                is APIState.Success -> {
                    ProductDetailsContent(
                        modifier = Modifier, productDetailsUI = apiState.data
                    )
                }
            }
        }
    }
}

@Composable
internal fun ProductDetailsContent(
    modifier: Modifier = Modifier,
    productDetailsUI: ProductDetailsUI
) {
    Text(
        modifier = modifier.padding(16.dp),
        text = productDetailsUI.description,
        style = MaterialTheme.typography.bodyMedium,
        fontSize = 18.sp,
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ProductDetailsScreen(
        modifier = Modifier,
        onBackPress = {},
        productDetailsViewModel = viewModel {
            ProductDetailsViewModel(productDetailsUseCase = object : ProductDetailsUseCase {
                override suspend fun invoke(): Flow<APIState<ProductDetailsUI>> {
                    return flow {
                        val text =
                            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim t. Duis aute irure dolor in reprehenderit in v "
                        emit(
                            APIState.Success(
                                ProductDetailsUI(
                                    id = 1,
                                    name = "Lorem Ipsum",
                                    description = (1..7).joinToString("\n") { text },
                                )
                            )
                        )
                    }
                }
            }, coroutineDispatcher = Dispatchers.Default)
        })
}

@Preview(showBackground = true)
@Composable
private fun PreviewLoading() {
    ProductDetailsScreen(
        modifier = Modifier,
        onBackPress = {},
        productDetailsViewModel = viewModel {
            ProductDetailsViewModel(productDetailsUseCase = object : ProductDetailsUseCase {
                override suspend fun invoke(): Flow<APIState<ProductDetailsUI>> {
                    return flow {
                        emit(
                            APIState.Loading
                        )
                    }
                }
            }, coroutineDispatcher = Dispatchers.Default)
        })
}
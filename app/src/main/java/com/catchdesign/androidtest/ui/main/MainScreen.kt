package com.catchdesign.androidtest.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.catchdesign.productdetails.ui.productDetailsScreen
import com.catchdesign.productdetails.ui.toProductDetailsScreen
import com.catchdesign.products.ui.Products
import com.catchdesign.products.ui.productsScreen


@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Products
    ) {
        productsScreen(onItemPress = navController::toProductDetailsScreen)
        productDetailsScreen(onBackPress = navController::popBackStack)
    }
}

@Preview
@Composable
private fun Preview() {
    MainScreen()
}
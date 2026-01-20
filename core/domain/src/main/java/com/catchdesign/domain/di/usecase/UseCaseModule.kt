package com.catchdesign.domain.di.usecase

import com.catchdesign.domain.usecase.productdetails.ProductDetailsUseCase
import com.catchdesign.domain.usecase.productdetails.ProductDetailsUseCaseImp
import com.catchdesign.domain.usecase.products.ProductsUseCase
import com.catchdesign.domain.usecase.products.ProductsUseCaseImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class UseCaseModule {

    @Binds
    abstract fun provideProductsUseCaseImp(productsUseCaseImp: ProductsUseCaseImp): ProductsUseCase

    @Binds
    abstract fun provideProductDetailsUseCaseImp(productDetailsUseCaseImp: ProductDetailsUseCaseImp): ProductDetailsUseCase


}
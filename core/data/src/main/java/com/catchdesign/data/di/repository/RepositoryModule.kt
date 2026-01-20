package com.catchdesign.data.di.repository

import com.catchdesign.data.repository.remote.products.ProductsRepository
import com.catchdesign.data.repository.remote.products.ProductsRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun provideProductsRepository(productsRepositoryImp: ProductsRepositoryImp): ProductsRepository
}



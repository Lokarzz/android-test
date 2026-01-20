package com.catchdesign.domain.usecase.productdetails

import com.catchdesign.data.repository.remote.products.ProductsRepository
import com.catchdesign.domain.model.APIState
import com.catchdesign.domain.model.productdetails.ProductDetailsUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


internal class ProductDetailsUseCaseImp @Inject constructor(private val productsRepository: ProductsRepository) :
    ProductDetailsUseCase {
    override suspend fun invoke(): Flow<APIState<ProductDetailsUI>> {
        return flow {
            emit(
                APIState.Success(
                    ProductDetailsUI(
                        id = 1,
                        name = "Product 1",
                        description = "Donec diam neque, vestibulum eget, vulputate ut, ultrices vel, augue. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec pharetra, magna vestibulum aliquet ultrices, erat tortor sollicitudin mi, sit amet lobortis sapien sapien non mi. Integer ac neque. Duis bibendum. Morbi non quam nec dui luctus rutrum. Nulla tellus. In sagittis dui vel nisl. Duis ac nibh. Fusce lacus purus, aliquet at, feugiat non, pretium quis, lectus. Suspendisse potenti. In eleifend quam a odio. In hac habitasse platea dictumst. Maecenas ut massa quis augue luctus tincidunt. Nulla mollis molestie lorem. Quisque ut erat. Curabitur gravida nisi at nibh. In hac habitasse platea dictumst. Aliquam augue quam, sollicitudin vitae, consectetuer eget, rutrum at, lorem. Integer tincidunt ante vel ipsum. Praesent blandit lacinia erat. Vestibulum sed magna at nunc commodo placerat. Praesent blandit. Nam nulla. Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede."
                    )
                )
            )
        }
    }
}
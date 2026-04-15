package com.youtubetrack.usecases

import com.youtubetrack.interfaces.ProductRepository
import com.youtubetrack.model.ProductEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// Location: domain/use_case/GetProductsUseCase.kt
class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    // 'operator fun invoke' allows you to call the use case like a function: getProductsUseCase()
    operator fun invoke(): Flow<List<ProductEntity>> {
        return repository.getProducts()
            .map { list -> list.sortedBy { it.title } } // Business Logic: Always sort by title
    }
}
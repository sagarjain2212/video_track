package com.youtubetrack.interfaces

import com.youtubetrack.model.ProductEntity
import kotlinx.coroutines.flow.Flow

// Location: domain/repository/ProductRepository.kt
interface ProductRepository {
    fun getProducts(): Flow<List<ProductEntity>>
    suspend fun addProduct(product: ProductEntity)
}
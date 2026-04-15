package com.youtubetrack.repository

import com.youtubetrack.interfaces.ProductDao
import com.youtubetrack.interfaces.ProductRepository
import com.youtubetrack.model.ProductEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// Location: data/repository/ProductRepositoryImpl.kt
class ProductRepositoryImpl @Inject constructor(
    private val dao: ProductDao
) : ProductRepository {

    override fun getProducts(): Flow<List<ProductEntity>> {
        return dao.getAllProducts()
    }

    override suspend fun addProduct(product: ProductEntity) {
        dao.insertProducts(listOf(product))
    }
}
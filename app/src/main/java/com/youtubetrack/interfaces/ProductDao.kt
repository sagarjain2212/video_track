package com.youtubetrack.interfaces

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.youtubetrack.model.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    // Flow emits a new list every time the database table changes
    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<ProductEntity>>

    // OnConflictStrategy.REPLACE ensures offline data is updated with new server data
    @Upsert
    suspend fun insertProducts(products: List<ProductEntity>)

    @Query("DELETE FROM products")
    suspend fun clearAll()
}
package com.youtubetrack.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.youtubetrack.interfaces.ProductDao
import com.youtubetrack.model.ProductEntity

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}
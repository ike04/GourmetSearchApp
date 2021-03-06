package com.google.codelab.gourmetsearchapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.google.codelab.gourmetsearchapp.model.local.FavoriteStoreEntity
import com.google.codelab.gourmetsearchapp.model.local.FilterEntity

@Database(entities = [FilterEntity::class, FavoriteStoreEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filterDataDao(): FilterDao

    abstract fun favoriteStoreIdDao(): FavoriteStoreDao
}

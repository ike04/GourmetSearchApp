package com.google.codelab.gourmetsearchapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.google.codelab.gourmetsearchapp.model.local.FilterEntity

@Database(entities = [FilterEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filterDataDao(): FilterDao
}

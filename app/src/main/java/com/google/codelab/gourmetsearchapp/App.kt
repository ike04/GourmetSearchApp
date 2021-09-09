package com.google.codelab.gourmetsearchapp

import android.app.Application
import androidx.room.Room
import com.google.codelab.gourmetsearchapp.data.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        // AppDatabaseをビルドする
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
}

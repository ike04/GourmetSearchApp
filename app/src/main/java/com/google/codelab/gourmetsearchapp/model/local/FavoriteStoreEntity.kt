package com.google.codelab.gourmetsearchapp.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_id")
data class FavoriteStoreEntity (
    @PrimaryKey
    val storeId: String
)

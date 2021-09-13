package com.google.codelab.gourmetsearchapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.google.codelab.gourmetsearchapp.model.local.FavoriteStoreEntity

@Dao
interface FavoriteStoreDao {
    @Query("SELECT * FROM favorite_id")
    fun loadFavoriteIds(): List<FavoriteStoreEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFavoriteStoreId(favoriteStoreEntity: FavoriteStoreEntity)

    @Query("DELETE FROM favorite_id WHERE storeId= :storeId")
    fun deleteStoreId(storeId: String)

    @Query("SELECT * FROM favorite_id WHERE storeId = :storeId")
    fun hasStoreId(storeId: String): List<FavoriteStoreEntity>
}

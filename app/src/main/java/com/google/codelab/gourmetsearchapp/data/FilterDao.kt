package com.google.codelab.gourmetsearchapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.google.codelab.gourmetsearchapp.model.local.FilterEntity

@Dao
interface FilterDao {

    @Query("SELECT * FROM filter_data")
    fun loadFilterDate(): FilterEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFilterData(filterEntity: FilterEntity)
}

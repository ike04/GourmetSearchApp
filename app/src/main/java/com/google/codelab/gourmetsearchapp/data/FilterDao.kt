package com.google.codelab.gourmetsearchapp.data

import androidx.room.*
import com.google.codelab.gourmetsearchapp.model.local.FilterEntity

@Dao
interface FilterDao {

    @Query("SELECT * FROM filter_data")
    fun loadFilterDate(): FilterEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFilterData(filterEntity: FilterEntity)

    @Query("DELETE FROM filter_data")
    fun deleteFilterData()
}

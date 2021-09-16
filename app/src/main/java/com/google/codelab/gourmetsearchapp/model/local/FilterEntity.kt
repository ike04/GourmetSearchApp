package com.google.codelab.gourmetsearchapp.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "filter_data")
data class FilterEntity(
    @PrimaryKey
    val id: Int,

    val searchRange: Int,
    val genre: String,
    val coupon: Int,
    val drink: Int,
    val privateRoom: Int,
    val wifi: Int,
    val lunch: Int
)

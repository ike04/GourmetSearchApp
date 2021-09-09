package com.google.codelab.gourmetsearchapp.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "filter_data")
data class FilterEntity(
    @PrimaryKey
    val id: Int,

    val searchRange: Int,
    val coupon: Boolean,
    val drink: Boolean,
    val privateRoom: Boolean,
    val wifi: Boolean,
    val lunch: Boolean
)

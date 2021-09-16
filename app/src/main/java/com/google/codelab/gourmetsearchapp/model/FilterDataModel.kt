package com.google.codelab.gourmetsearchapp.model

data class FilterDataModel(
    val searchRange: Int,
    val genre: String,
    val coupon: Int,
    val drink: Int,
    val privateRoom: Int,
    val wifi: Int,
    val lunch: Int
)

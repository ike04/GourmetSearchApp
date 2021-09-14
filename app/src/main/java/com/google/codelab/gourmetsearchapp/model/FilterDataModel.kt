package com.google.codelab.gourmetsearchapp.model

data class FilterDataModel(
    val searchRange: Int,
    val genre: String,
    val coupon: Boolean,
    val drink: Boolean,
    val privateRoom: Boolean,
    val wifi: Boolean,
    val lunch: Boolean
)

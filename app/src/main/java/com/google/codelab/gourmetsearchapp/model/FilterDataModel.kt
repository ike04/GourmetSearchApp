package com.google.codelab.gourmetsearchapp.model

data class FilterDataModel(
    val searchRange: Int = 3,
    val genre: String = "",
    val coupon: Int = 0,
    val drink: Int = 0,
    val privateRoom: Int = 0,
    val wifi: Int = 0,
    val lunch: Int = 0,
    val keyword: String = ""
)

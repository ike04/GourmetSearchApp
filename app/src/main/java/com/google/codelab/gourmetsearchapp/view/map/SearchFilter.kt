package com.google.codelab.gourmetsearchapp.view.map

import androidx.annotation.StringRes
import com.google.codelab.gourmetsearchapp.R

enum class SearchFilter(val id: Int, @StringRes val title: Int, val range: Int) {
    FIRST(R.id.range_300, R.string.text_filter_range_checkbox_1, 1),
    SECOND(R.id.range_500, R.string.text_filter_range_checkbox_2, 2),
    THIRD(R.id.range_1000, R.string.text_filter_range_checkbox_3, 3),
    FORTH(R.id.range_2000, R.string.text_filter_range_checkbox_4, 4),
    FIFTH(R.id.range_3000, R.string.text_filter_range_checkbox_5, 5);

    companion object {
        fun getRange(id: Int): SearchFilter {
            return when (id) {
                FIRST.id -> FIRST
                SECOND.id -> SECOND
                THIRD.id -> THIRD
                FORTH.id -> FORTH
                FIFTH.id -> FIFTH
                else -> THIRD
            }
        }

        fun getId(range: Int): SearchFilter {
            return when (range) {
                FIRST.range -> FIRST
                SECOND.range -> SECOND
                THIRD.range -> THIRD
                FORTH.range -> FORTH
                FIFTH.range -> FIFTH
                else -> THIRD
            }
        }
    }
}

enum class SearchChips(@StringRes val genre: Int, val code: String) {
    PUB(R.string.text_filter_chip_1, "G001"),
    BAR(R.string.text_filter_chip_2, "G002"),
    JAPANESE(R.string.text_filter_chip_3, "G004"),
    WESTERN(R.string.text_filter_chip_4, "G005"),
    ITALIAN(R.string.text_filter_chip_5, "G006"),
    CHINESE(R.string.text_filter_chip_6, "G007"),
    GRILLED_MEAT(R.string.text_filter_chip_7, "G008"),
    CAFE(R.string.text_filter_chip_8, "G014");

    companion object {
        fun getCode(id: Int): String {
            return if (id != -1) {
                values()[id].code
            } else {
                ""
            }
        }


        fun getId(code: String): Int {
            var id = -1 // not exist
            values().forEachIndexed { index, searchChips ->
                if (searchChips.code == code) {
                    id = index
                }
            }
            return id
        }
    }
}

package com.google.codelab.gourmetsearchapp.view.onboarding

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.google.codelab.gourmetsearchapp.R

enum class Onboarding(@StringRes val title: Int, @StringRes val message: Int, @RawRes val animation: Int, val position: Int){
    FIRST(R.string.text_app_onboarding_title01, R.string.text_onboarding_instruction01, R.raw.onboarding_image1, 0),
    SECOND(R.string.text_app_onboarding_title02, R.string.text_onboarding_instruction02, R.raw.onboarding_image2, 1),
    LAST(R.string.text_app_onboarding_title03, R.string.text_onboarding_instruction03, R.raw.onboarding_image3, 2);

    companion object {
        val data = listOf(FIRST, SECOND, LAST)
    }
}

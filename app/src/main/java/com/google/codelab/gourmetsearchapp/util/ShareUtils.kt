package com.google.codelab.gourmetsearchapp.util

import android.content.Intent

object ShareUtils {
    fun share(url: String): Intent {
        return Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "${url}を共有する。")
            type = "text/plain"
        }
    }
}

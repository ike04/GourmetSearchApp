package com.google.codelab.gourmetsearchapp.ext

import android.content.Context
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import com.google.codelab.gourmetsearchapp.view.map.MapsFragment

object ContextExt {
    fun Context.showAlertDialog(
        @StringRes title: Int,
        @StringRes message: Int,
        fragmentManager: FragmentManager
    ) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                MapsFragment.newInstance().showFragment(fragmentManager, true)
            }.setOnDismissListener {
                MapsFragment.newInstance().showFragment(fragmentManager, true)
            }
            .show()
    }
}

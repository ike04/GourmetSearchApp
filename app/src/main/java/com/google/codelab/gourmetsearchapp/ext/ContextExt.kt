package com.google.codelab.gourmetsearchapp.ext

import android.content.Context
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.codelab.gourmetsearchapp.R

object ContextExt {
    fun Context.showAlertDialog(
        @StringRes title: Int,
        @StringRes message: Int,
        fragment: Fragment
    ) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                fragment.findNavController().navigate(R.id.navigation_map)
            }.setOnDismissListener {
                fragment.findNavController().navigate(R.id.navigation_map)
            }
            .show()
    }
}

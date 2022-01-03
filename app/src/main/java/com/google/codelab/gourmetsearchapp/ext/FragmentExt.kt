package com.google.codelab.gourmetsearchapp.ext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import com.google.codelab.gourmetsearchapp.R
import com.google.codelab.gourmetsearchapp.model.Failure

fun Fragment.showFragment(fragmentManager: FragmentManager, setToRoot: Boolean = false) {
    if (setToRoot) {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        fragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.nav_host_fragment_activity_main, this)
            .commit()
    } else {
        fragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.nav_host_fragment_activity_main, this)
            .addToBackStack(null)
            .commit()
    }
}

fun Fragment.showSnackBarWithAction(failure: Failure) {
    Snackbar.make(this.requireView(), failure.message, Snackbar.LENGTH_LONG)
        .setAction(R.string.retry) { failure.resolve() }
        .show()
}

fun Fragment.showSnackBarWithActionInfinity(message: Int, action :() -> Unit) {
    Snackbar.make(this.requireView(), message, Snackbar.LENGTH_INDEFINITE)
        .setAction(R.string.text_location_permission) { action.invoke() }
        .show()
}

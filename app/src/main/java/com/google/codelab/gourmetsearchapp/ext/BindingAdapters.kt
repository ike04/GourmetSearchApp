package com.google.codelab.gourmetsearchapp.ext

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("imageUrl", "defaultImage")
    fun ImageView.loadImageUrl(url: String?, defaultImage: Drawable) {
        Glide.with(context)
            .load(url)
            .circleCrop()
            .error(defaultImage)
            .into(this)
    }

    @JvmStatic
    @BindingAdapter("showProgress")
    fun ProgressBar.showProgressBar(isShow: Boolean) {
        this.isVisible = isShow
    }
}

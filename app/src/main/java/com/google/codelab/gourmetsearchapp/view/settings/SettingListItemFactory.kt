package com.google.codelab.gourmetsearchapp.view.settings

import android.view.View
import androidx.annotation.StringRes
import com.google.codelab.gourmetsearchapp.R
import com.google.codelab.gourmetsearchapp.databinding.CellSettingBinding
import com.xwray.groupie.viewbinding.BindableItem

class SettingListItemFactory(val item: SettingsList) : BindableItem<CellSettingBinding>() {
    override fun getLayout() = R.layout.cell_setting

    override fun bind(viewBinding: CellSettingBinding, position: Int) {
        viewBinding.item = item
    }

    override fun initializeViewBinding(view: View): CellSettingBinding {
        return CellSettingBinding.bind(view)
    }
}

enum class SettingsList(@StringRes val title: Int) {
    APP_SETTING(R.string.text_cell_title1),
    ONBOARDING(R.string.text_cell_title2),
    PRIVACY_POLICY(R.string.text_cell_title3);
}

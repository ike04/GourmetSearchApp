package com.google.codelab.gourmetsearchapp.view.home

import android.content.Context
import android.view.View
import androidx.annotation.StringRes
import com.google.codelab.gourmetsearchapp.R
import com.google.codelab.gourmetsearchapp.databinding.CellEmptyBinding
import com.google.codelab.gourmetsearchapp.databinding.CellStoreBinding
import com.google.codelab.gourmetsearchapp.model.businessmodel.Store
import com.xwray.groupie.Item
import com.xwray.groupie.viewbinding.BindableItem

class StoreItem(private val store: Store, val context: Context) :
    BindableItem<CellStoreBinding>() {
    override fun getLayout() = R.layout.cell_store

    override fun bind(viewBinding: CellStoreBinding, position: Int) {
        viewBinding.item = store
    }

    override fun initializeViewBinding(view: View): CellStoreBinding {
        return CellStoreBinding.bind(view)
    }

    override fun isSameAs(other: Item<*>): Boolean = (other as? StoreItem)?.store?.id == store.id
}

class EmptyItem(@StringRes private val message: Int, val context: Context) : BindableItem<CellEmptyBinding>() {
    override fun bind(viewBinding: CellEmptyBinding, position: Int) {
        viewBinding.text = context.resources.getString(message)
    }

    override fun getLayout(): Int = R.layout.cell_empty

    override fun initializeViewBinding(view: View): CellEmptyBinding = CellEmptyBinding.bind(view)
}

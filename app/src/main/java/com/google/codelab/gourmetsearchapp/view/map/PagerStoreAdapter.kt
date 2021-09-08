package com.google.codelab.gourmetsearchapp.view.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.codelab.gourmetsearchapp.databinding.PagerStoreBinding
import com.google.codelab.gourmetsearchapp.model.businessmodel.Store

class PagerStoreAdapter(
    private val store: List<Store>,
    private val onCellClick: (Store) -> Unit
) :
    RecyclerView.Adapter<PagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PagerStoreBinding.inflate(layoutInflater, parent, false)
        return PagerViewHolder(binding)
    }


    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(store[position])
        holder.itemView.setOnClickListener {
            onCellClick(store[position])
        }
    }

    override fun getItemCount(): Int = store.size
}

class PagerViewHolder(val binding: PagerStoreBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(store: Store) {
        binding.item = store
    }
}

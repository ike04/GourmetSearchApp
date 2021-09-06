package com.google.codelab.gourmetsearchapp.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.codelab.gourmetsearchapp.databinding.FragmentFavoriteStoresBinding


class FavoriteStoresFragment : Fragment() {

    private lateinit var favoriteStoresViewModel: FavoriteStoresViewModel
    private lateinit var binding: FragmentFavoriteStoresBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favoriteStoresViewModel =
            ViewModelProvider(this).get(FavoriteStoresViewModel::class.java)

        binding = FragmentFavoriteStoresBinding.inflate(inflater, container, false)

        val textView: TextView = binding.textDashboard
        favoriteStoresViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return binding.root
    }
}

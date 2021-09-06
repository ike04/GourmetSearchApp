package com.google.codelab.gourmetsearchapp.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.codelab.gourmetsearchapp.databinding.FragmentFavoriteStoresBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteStoresFragment : Fragment() {

    private val viewModel: FavoriteStoresViewModel by viewModels()
    private lateinit var binding: FragmentFavoriteStoresBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteStoresBinding.inflate(inflater, container, false)

        val textView: TextView = binding.textDashboard
        viewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return binding.root
    }
}

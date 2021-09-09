package com.google.codelab.gourmetsearchapp.view.map

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.codelab.gourmetsearchapp.R
import com.google.codelab.gourmetsearchapp.databinding.FragmentSearchFilterBinding
import com.google.codelab.gourmetsearchapp.viewmodel.MapsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFilterFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentSearchFilterBinding
    private val parentViewModel: MapsViewModel by viewModels()

    companion object {
        fun newInstance(): SearchFilterFragment {
            return SearchFilterFragment()
        }

    }
    override fun setupDialog(dialog: Dialog, style: Int) {
        binding = FragmentSearchFilterBinding.inflate(LayoutInflater.from(context), null, false)
        dialog.setContentView(binding.root)
    }
}

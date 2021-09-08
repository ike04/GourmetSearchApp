package com.google.codelab.gourmetsearchapp.view.map

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.codelab.gourmetsearchapp.R
import com.google.codelab.gourmetsearchapp.databinding.FragmentSearchFilterBinding

class SearchFilterFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentSearchFilterBinding

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

package com.google.codelab.gourmetsearchapp.view.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.codelab.gourmetsearchapp.databinding.FragmentSearchFilterBinding
import com.google.codelab.gourmetsearchapp.databinding.FragmentSwitchHomeListBinding
import com.google.codelab.gourmetsearchapp.view.map.SearchFilterDialogFragment

class SwitchHomeListFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentSwitchHomeListBinding

    companion object {
        fun newInstance(): SwitchHomeListFragment {
            return SwitchHomeListFragment()
        }
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        binding = FragmentSwitchHomeListBinding.inflate(LayoutInflater.from(context), null, false)
        dialog.setContentView(binding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}

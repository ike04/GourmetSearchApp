package com.google.codelab.gourmetsearchapp.view.map

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.codelab.gourmetsearchapp.databinding.FragmentSearchFilterBinding
import com.google.codelab.gourmetsearchapp.viewmodel.MapsViewModel
import com.google.codelab.gourmetsearchapp.viewmodel.SearchFilterDialogViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy

@AndroidEntryPoint
class SearchFilterDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentSearchFilterBinding
    private val parentViewModel: MapsViewModel by viewModels()
    private val viewModel: SearchFilterDialogViewModel by viewModels()
    private val disposable = CompositeDisposable()

    companion object {
        fun newInstance(): SearchFilterDialogFragment {
            return SearchFilterDialogFragment()
        }
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        binding = FragmentSearchFilterBinding.inflate(LayoutInflater.from(context), null, false)
        binding.viewModel = viewModel
        dialog.setContentView(binding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.onSearchClicked
            .subscribeBy { dismiss() }
            .addTo(disposable)
    }

    private fun createSearchCondition() {

    }
}

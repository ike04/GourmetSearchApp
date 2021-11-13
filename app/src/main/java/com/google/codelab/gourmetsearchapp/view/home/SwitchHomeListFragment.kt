package com.google.codelab.gourmetsearchapp.view.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.codelab.gourmetsearchapp.databinding.FragmentSwitchHomeListBinding
import com.google.codelab.gourmetsearchapp.viewmodel.HomeViewModel
import com.google.codelab.gourmetsearchapp.viewmodel.SwitchHomeListViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy

@AndroidEntryPoint
class SwitchHomeListFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentSwitchHomeListBinding
    private val viewModel: SwitchHomeListViewModel by viewModels()
    private val parentViewModel: HomeViewModel by activityViewModels()
    private val disposable = CompositeDisposable()

    companion object {
        fun newInstance(): SwitchHomeListFragment {
            return SwitchHomeListFragment()
        }
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        binding = FragmentSwitchHomeListBinding.inflate(LayoutInflater.from(context), null, false)
        binding.viewModel = viewModel
        dialog.setContentView(binding.root)

        toggleRadioButton()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.onClick
            .subscribeBy {
                dismiss()
                parentViewModel.resetStoreList()
                parentViewModel.resetPages()
                parentViewModel.selectedFavorite.set(binding.favoriteStoreRadioButton.isChecked)
                if (parentViewModel.selectedFavorite.get()) {
                    parentViewModel.fetchFavoriteStores(true)
                } else {
                    parentViewModel.checkLocationPermission()
                }
            }.addTo(disposable)
    }

    private fun toggleRadioButton() {
        if (parentViewModel.selectedFavorite.get()) {
            binding.favoriteStoreRadioButton.isChecked = true
        } else {
            binding.nearStoreRadioButton.isChecked = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

}

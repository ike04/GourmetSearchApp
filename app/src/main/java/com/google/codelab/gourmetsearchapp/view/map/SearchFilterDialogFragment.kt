package com.google.codelab.gourmetsearchapp.view.map

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.CheckBox
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.codelab.gourmetsearchapp.R
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
    private val parentViewModel: MapsViewModel by activityViewModels()
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
            .subscribeBy {
                dismiss()
                parentViewModel.resetStores()
                fetchFilterConditionStores()
            }.addTo(disposable)
    }

    private fun fetchFilterConditionStores() {
        val searchRange = getSearchRange()
        val couponFlg = getCheckboxFlag(binding.checkBoxCoupon)
        val drinkFlg = getCheckboxFlag(binding.checkBoxDrink)
        val privateRoomFlg = getCheckboxFlag(binding.checkBoxPrivateRoom)
        val wifiFlg = getCheckboxFlag(binding.checkBoxWifi)
        val lunchFlg = getCheckboxFlag(binding.checkBoxLunch)

        parentViewModel.fetchNearStores(
            searchRange,
            couponFlg,
            drinkFlg,
            privateRoomFlg,
            wifiFlg,
            lunchFlg
        )
    }

    private fun getSearchRange(): Int {
        return when (binding.radioGroup.checkedRadioButtonId) {
            R.id.range_300 -> 1
            R.id.range_500 -> 2
            R.id.range_1000 -> 3
            R.id.range_2000 -> 4
            R.id.range_3000 -> 5
            else -> 3
        }
    }

    private fun getCheckboxFlag(checkbox: CheckBox): Int {
        return if (checkbox.isChecked) {
            1
        } else {
            0
        }
    }
}

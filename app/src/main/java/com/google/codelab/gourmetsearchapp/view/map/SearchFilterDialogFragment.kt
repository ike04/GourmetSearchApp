package com.google.codelab.gourmetsearchapp.view.map

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.codelab.gourmetsearchapp.R
import com.google.codelab.gourmetsearchapp.databinding.FragmentSearchFilterBinding
import com.google.codelab.gourmetsearchapp.model.FilterDataModel
import com.google.codelab.gourmetsearchapp.viewmodel.MapsViewModel
import com.google.codelab.gourmetsearchapp.viewmodel.SearchFilterDialogViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

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

        viewModel.fetchFilterData()

        viewModel.filterData
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { setFilterCondition(it)}
            .addTo(disposable)

        viewModel.onSearchClicked
            .subscribeBy {
                dismiss()
                parentViewModel.resetStores()
                fetchFilterConditionStores()
            }.addTo(disposable)

        viewModel.onCancelClicked
            .subscribeBy { dismiss() }
            .addTo(disposable)
    }

    private fun setFilterCondition(filterData: FilterDataModel) {
        binding.radioGroup.check(
            when (filterData.searchRange) {
                1 -> R.id.range_300
                2 -> R.id.range_500
                3 -> R.id.range_1000
                4 -> R.id.range_2000
                5 -> R.id.range_3000
                else -> R.id.range_1000
            }
        )
        binding.apply {
            checkBoxCoupon.isChecked = filterData.coupon
            checkBoxDrink.isChecked = filterData.drink
            checkBoxPrivateRoom.isChecked = filterData.privateRoom
            checkBoxWifi.isChecked = filterData.wifi
            checkBoxLunch.isChecked = filterData.lunch
        }

    }

    private fun fetchFilterConditionStores() {
        val model = FilterDataModel(
            searchRange = getSearchRange(),
            coupon = binding.checkBoxCoupon.isChecked,
            drink = binding.checkBoxDrink.isChecked,
            privateRoom = binding.checkBoxPrivateRoom.isChecked,
            wifi = binding.checkBoxWifi.isChecked,
            lunch = binding.checkBoxLunch.isChecked
        )

        viewModel.saveFilterData(model)

        parentViewModel.fetchNearStores(
            model.searchRange,
            getCheckboxFlag(model.coupon),
            getCheckboxFlag(model.drink),
            getCheckboxFlag(model.privateRoom),
            getCheckboxFlag(model.wifi),
            getCheckboxFlag(model.lunch)
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

    private fun getCheckboxFlag(flg: Boolean): Int {
        return if (flg) {
            1
        } else {
            0
        }
    }
}

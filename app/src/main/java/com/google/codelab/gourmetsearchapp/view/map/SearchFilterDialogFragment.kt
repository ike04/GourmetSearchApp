package com.google.codelab.gourmetsearchapp.view.map

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
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
        private const val CHECK = 1
        fun newInstance(): SearchFilterDialogFragment {
            return SearchFilterDialogFragment()
        }
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        binding = FragmentSearchFilterBinding.inflate(LayoutInflater.from(context), null, false)
        binding.viewModel = viewModel
        dialog.setContentView(binding.root)

        createChips()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.fetchFilterData()

        viewModel.filterData
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { setFilterCondition(it) }
            .addTo(disposable)

        viewModel.onSearchClicked
            .subscribeBy {
                dismiss()
                parentViewModel.resetStores()
                fetchFilterConditionStores()
            }.addTo(disposable)

        viewModel.onResetClicked
            .subscribeBy {
                viewModel.resetFilter()
                resetFilterSetting()
            }.addTo(disposable)

        viewModel.onCancelClicked
            .subscribeBy { dismiss() }
            .addTo(disposable)
    }

    private fun setFilterCondition(filterData: FilterDataModel) {
        binding.radioGroup.check(SearchFilter.getId(filterData.searchRange).id)
        binding.apply {
            chipGroup.check(SearchChips.getId(filterData.genre))
            checkBoxCoupon.isChecked = filterData.coupon == CHECK
            checkBoxDrink.isChecked = filterData.drink == CHECK
            checkBoxPrivateRoom.isChecked = filterData.privateRoom == CHECK
            checkBoxWifi.isChecked = filterData.wifi == CHECK
            checkBoxLunch.isChecked = filterData.lunch == CHECK
            keywordText.setText(filterData.keyword)
        }

    }

    private fun fetchFilterConditionStores() {
        val model = FilterDataModel(
            searchRange = SearchFilter.getRange(binding.radioGroup.checkedRadioButtonId).range,
            genre = SearchChips.getCode(binding.chipGroup.checkedChipId),
            coupon = getCheckboxFlag(binding.checkBoxCoupon.isChecked),
            drink = getCheckboxFlag(binding.checkBoxDrink.isChecked),
            privateRoom = getCheckboxFlag(binding.checkBoxPrivateRoom.isChecked),
            wifi = getCheckboxFlag(binding.checkBoxWifi.isChecked),
            lunch = getCheckboxFlag(binding.checkBoxLunch.isChecked),
            keyword = binding.keywordText.text.toString()
        )

        viewModel.saveFilterData(model)

        parentViewModel.fetchNearStores()
    }

    private fun getCheckboxFlag(flg: Boolean): Int {
        return if (flg) {
            1
        } else {
            0
        }
    }

    private fun resetFilterSetting() {
        binding.apply {
            radioGroup.check(R.id.range_1000)
            chipGroup.clearCheck()
            checkBoxCoupon.isChecked = false
            checkBoxDrink.isChecked = false
            checkBoxPrivateRoom.isChecked = false
            checkBoxWifi.isChecked = false
            checkBoxLunch.isChecked = false
            keywordText.setText("")
        }
    }

    private fun createChips() {
        binding.chipGroup.isSingleSelection = true
        SearchChips.values().forEachIndexed { index, searchChips ->
            // https://stackoverflow.com/questions/53557863/change-chip-widget-style-programmatically-not-working-android
            val chip = Chip(requireContext())
            val drawable = ChipDrawable.createFromAttributes(requireContext(), null, 0, R.style.Widget_MaterialComponents_Chip_Filter)

            chip.apply {
                setChipDrawable(drawable)
                id = index
                text = requireContext().resources.getText(searchChips.genre)
                rippleColor = ContextCompat.getColorStateList(requireContext(), R.color.chip_color_selector)
                chipBackgroundColor = ContextCompat.getColorStateList(requireContext(), R.color.chip_color_selector)
            }
            binding.chipGroup.addView(chip)
        }
    }
}

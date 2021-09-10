package com.google.codelab.gourmetsearchapp.view.webview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.codelab.gourmetsearchapp.databinding.StoreWebViewFragmentBinding
import com.google.codelab.gourmetsearchapp.viewmodel.StoreWebViewViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

class StoreWebViewFragment : Fragment() {
    private lateinit var binding: StoreWebViewFragmentBinding
    private val viewModel: StoreWebViewViewModel by viewModels()
    private val storeId: String
        get() = checkNotNull(arguments?.getString(STORE_ID))

    private val url: String
        get() = checkNotNull(arguments?.getString(URL))

    private val disposables = CompositeDisposable()

    companion object {
        private const val STORE_ID = "store_id"
        private const val URL = "url"
        fun newInstance(
            storeId: String,
            url: String
        ): StoreWebViewFragment {
            return StoreWebViewFragment().apply {
                arguments = Bundle().apply {
                    putString(STORE_ID, storeId)
                    putString(URL, url)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = StoreWebViewFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                parentFragmentManager.popBackStack()
                true
            }
            else -> {
                true
            }
        }
    }
}

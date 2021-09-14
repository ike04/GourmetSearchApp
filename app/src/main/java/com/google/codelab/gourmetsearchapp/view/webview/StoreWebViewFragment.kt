package com.google.codelab.gourmetsearchapp.view.webview

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.google.codelab.gourmetsearchapp.R
import com.google.codelab.gourmetsearchapp.databinding.StoreWebViewFragmentBinding
import com.google.codelab.gourmetsearchapp.util.ShareUtils
import com.google.codelab.gourmetsearchapp.view.OnBackPressHandler
import com.google.codelab.gourmetsearchapp.viewmodel.StoreWebViewViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy

@AndroidEntryPoint
class StoreWebViewFragment : Fragment(), OnBackPressHandler {
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
        binding.viewModel = viewModel
        binding.url = url
        binding.storeId = storeId

        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        viewModel.addFavoriteStore
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                Toast.makeText(requireContext(), R.string.text_add_favorite, Toast.LENGTH_SHORT)
                    .show()
            }.addTo(disposables)

        viewModel.deleteFavoriteStore
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                Toast.makeText(requireContext(), R.string.text_delete_favorite, Toast.LENGTH_SHORT)
                    .show()
            }.addTo(disposables)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchFavoriteStore(storeId)

        viewModel.error
            .subscribeBy { failure ->
                Snackbar.make(view, failure.message, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.retry) { failure.retry }
                    .show()
            }.addTo(disposables)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.web_view_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                parentFragmentManager.popBackStack()
                true
            }
            R.id.share -> {
                val sendIntent = ShareUtils.share(url)
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
                true
            }
            else -> true
        }
    }

    override fun onBackPressed(): Boolean {
        parentFragmentManager.popBackStack()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}

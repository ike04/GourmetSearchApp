package com.google.codelab.gourmetsearchapp.view.webview

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.codelab.gourmetsearchapp.R
import com.google.codelab.gourmetsearchapp.databinding.ActivityWebViewBinding
import com.google.codelab.gourmetsearchapp.util.ShareUtils
import com.google.codelab.gourmetsearchapp.viewmodel.StoreWebViewViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy

@AndroidEntryPoint
class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding
    private val viewModel: StoreWebViewViewModel by viewModels()
    private lateinit var storeId: String
    private lateinit var url: String
    private val disposables = CompositeDisposable()

    companion object {
        const val ID = "id"
        const val URL = "url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)

        setContentView(binding.root)

        storeId = intent.getStringExtra(ID).toString()
        url = intent.getStringExtra(URL).toString()

        binding.viewModel = viewModel
        binding.url = url
        binding.storeId = storeId

        title = getString(R.string.text_web_view_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.addFavoriteStore
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                Toast.makeText(this, R.string.text_add_favorite, Toast.LENGTH_SHORT)
                    .show()
            }.addTo(disposables)

        viewModel.deleteFavoriteStore
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                Toast.makeText(this, R.string.text_delete_favorite, Toast.LENGTH_SHORT)
                    .show()
            }.addTo(disposables)
    }

    override fun onStart() {
        super.onStart()

        viewModel.fetchFavoriteStore(storeId)

        viewModel.error
            .subscribeBy { failure ->
                Snackbar.make(binding.root, failure.message, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.retry) { failure.retry }
                    .show()
            }.addTo(disposables)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.web_view_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
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

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}

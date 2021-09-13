package com.google.codelab.gourmetsearchapp.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.codelab.gourmetsearchapp.R
import com.google.codelab.gourmetsearchapp.databinding.FragmentHomeBinding
import com.google.codelab.gourmetsearchapp.ext.ContextExt
import com.google.codelab.gourmetsearchapp.ext.ContextExt.showAlertDialog
import com.google.codelab.gourmetsearchapp.ext.showFragment
import com.google.codelab.gourmetsearchapp.model.businessmodel.Store
import com.google.codelab.gourmetsearchapp.view.webview.StoreWebViewFragment
import com.google.codelab.gourmetsearchapp.viewmodel.HomeViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()
    private val storeList: MutableList<Store> = ArrayList()

    private val disposable = CompositeDisposable()

    private val onItemClickListener = OnItemClickListener { item, _ ->
        val index = groupAdapter.getAdapterPosition(item)

        StoreWebViewFragment.newInstance(
            storeList[index].id,
            storeList[index].urls
        ).showFragment(parentFragmentManager)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            adapter = groupAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        }

        viewModel.checkLocationPermission()

        viewModel.hasLocation
            .subscribeBy { hasLocation ->
                if (hasLocation) {
                    viewModel.fetchStores()
                } else {
                    requireContext().showAlertDialog(
                        R.string.no_locations_title,
                        R.string.no_locations_message,
                        parentFragmentManager
                    )
                }
            }.addTo(disposable)

        viewModel.storeList
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { stores ->
                if(stores.store.isNotEmpty()) {
                    storeList.addAll(stores.store)
                    groupAdapter.update(storeList.map { StoreItem(it, requireContext()) })
                } else{
                    groupAdapter.update(listOf(EmptyItem("検索条件にあったレストランが見つかりませんでした。")))
                }
            }.addTo(disposable)

        viewModel.error
            .subscribeBy { failure ->
                Snackbar.make(view, failure.message, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.retry) { failure.retry }
                    .show()
            }.addTo(disposable)

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && viewModel.moreLoad.get()) {
                    viewModel.fetchStores()
                }
            }
        })

        groupAdapter.setOnItemClickListener(onItemClickListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}

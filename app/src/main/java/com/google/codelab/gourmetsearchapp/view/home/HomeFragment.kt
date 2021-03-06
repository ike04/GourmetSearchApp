package com.google.codelab.gourmetsearchapp.view.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import autoCleared
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.codelab.gourmetsearchapp.R
import com.google.codelab.gourmetsearchapp.databinding.FragmentHomeBinding
import com.google.codelab.gourmetsearchapp.ext.showSnackBarWithAction
import com.google.codelab.gourmetsearchapp.model.businessmodel.Store
import com.google.codelab.gourmetsearchapp.view.widget.GeneralDialogFragment
import com.google.codelab.gourmetsearchapp.view.webview.WebViewActivity
import com.google.codelab.gourmetsearchapp.viewmodel.HomeViewModel
import com.google.codelab.gourmetsearchapp.viewmodel.MainViewModel
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
    private val parentViewModel: MainViewModel by activityViewModels()
    private val viewModel: HomeViewModel by activityViewModels()
    private var binding: FragmentHomeBinding by autoCleared()
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()
    private val storeList: MutableList<Store> = ArrayList()
    private var hasNoStores = true

    private val disposable = CompositeDisposable()

    private val onItemClickListener = OnItemClickListener { item, _ ->
        val index = groupAdapter.getAdapterPosition(item)

        if (!hasNoStores) {
            val intent = Intent(requireContext(), WebViewActivity::class.java)
                .putExtra(WebViewActivity.ID, storeList[index].id)
                .putExtra(WebViewActivity.URL, storeList[index].urls)
            startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)

        binding.swipedLayout.setOnRefreshListener {
            storeList.clear()
            viewModel.resetPages()
            if (viewModel.selectedFavorite.get()) {
                viewModel.fetchFavoriteStores(true)
            } else {
                viewModel.checkLocationPermission()
            }
            binding.swipedLayout.isRefreshing = false
        }

        binding.recyclerView.adapter = groupAdapter

        viewModel.hasLocation
            .subscribeBy { hasLocation ->
                if (hasLocation) {
                    viewModel.fetchStores(true)
                } else {
                    GeneralDialogFragment.Builder.from(this)
                        .setTitle(R.string.no_locations_title)
                        .setMessage(R.string.no_locations_message)
                        .setPositiveButton(R.string.text_open_app_settings) {
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri = Uri.fromParts(
                                "package",
                                requireContext().packageName,
                                null
                            )
                            intent.data = uri
                            startActivity(intent)
                        }
                        .build()
                        .show(childFragmentManager, "")
                }
            }.addTo(disposable)

        viewModel.storeList
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { stores ->
                if (stores.store.isNotEmpty()) {
                    storeList.addAll(stores.store)
                    groupAdapter.update(storeList.map { StoreItem(it, requireContext()) })
                    bottomNav.getOrCreateBadge(R.id.navigation_home).apply {
                        isVisible = true
                        number = stores.totalPages
                    }
                }
            }.addTo(disposable)

        viewModel.isEmptyStores
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { isEmpty ->
                binding.recyclerView.layoutManager = if(isEmpty) {
                    val message = if (viewModel.selectedFavorite.get()) {
                        R.string.no_result_favorite_restaurant
                    } else {
                        R.string.no_result_near_restaurant
                    }
                    groupAdapter.update(listOf(EmptyItem(message, requireContext())))
                    bottomNav.getOrCreateBadge(R.id.navigation_home).isVisible = false
                    GridLayoutManager(requireContext(), 1, GridLayoutManager.VERTICAL, false)
                } else {
                    GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
                }
                hasNoStores = isEmpty
            }.addTo(disposable)

        viewModel.reset
            .subscribeBy {
                storeList.clear()
            }.addTo(disposable)

        parentViewModel.reselectItem
            .filter { it.isHome() }
            .subscribeBy { binding.recyclerView.smoothScrollToPosition(0) }
            .addTo(disposable)

        viewModel.error
            .subscribeBy { failure ->
                showSnackBarWithAction(failure)
            }.addTo(disposable)

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && viewModel.moreLoad.get()) {
                    if (viewModel.selectedFavorite.get()) {
                        viewModel.fetchFavoriteStores()
                    } else {
                        viewModel.fetchStores()
                    }
                }
            }
        })

        viewModel.checkLocationPermission()
        groupAdapter.setOnItemClickListener(onItemClickListener)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite -> {
                SwitchHomeListFragment.newInstance().show(childFragmentManager, "")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}

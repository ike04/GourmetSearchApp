package com.google.codelab.gourmetsearchapp.view.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.viewpager.widget.ViewPager
import com.google.codelab.gourmetsearchapp.R
import com.google.codelab.gourmetsearchapp.databinding.ActivityOnboardingBinding
import com.google.codelab.gourmetsearchapp.util.MapUtils
import com.google.codelab.gourmetsearchapp.view.MainActivity
import com.google.codelab.gourmetsearchapp.viewmodel.OnboardingViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private val viewModel: OnboardingViewModel by viewModels()
    private lateinit var pagerAdapter: OnboardingPagerAdapter

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        binding.viewModel = viewModel

        supportActionBar?.hide()
        MapUtils.requestLocationPermission(this, this)
        setContentView(binding.root)
        setOnboarding()
        setupViews()

        viewModel.onClick
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                val intent = Intent(application, MainActivity::class.java)
                startActivity(intent)
                finish()
            }.addTo(disposables)
    }

    private fun setOnboarding() {
        val onboard = Onboarding.data
        pagerAdapter = OnboardingPagerAdapter(supportFragmentManager, onboard)
        binding.viewPager.adapter = pagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager, true)
    }

    private fun setupViews() {
        var currentFragment: OnboardingFragment? = null

        binding.viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageScrollStateChanged(state: Int) {
                when (state) {
                    ViewPager.SCROLL_STATE_DRAGGING -> currentFragment?.pauseAnimation()
                    ViewPager.SCROLL_STATE_IDLE -> currentFragment?.resumeAnimation()
                }
            }

            override fun onPageSelected(position: Int) {
                currentFragment = pagerAdapter.getFragmentByPosition(position)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}

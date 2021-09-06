package com.google.codelab.gourmetsearchapp.view.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.codelab.gourmetsearchapp.R
import com.google.codelab.gourmetsearchapp.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var pagerAdapter: OnboardingPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)

        supportActionBar?.hide()
        setContentView(binding.root)
        setOnboarding()
        setupViews()
    }

    private fun setOnboarding() {
        val onboard = Onboarding.data
        pagerAdapter = OnboardingPagerAdapter(supportFragmentManager, onboard)
        binding.viewPager.adapter = pagerAdapter
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

}

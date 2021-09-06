package com.google.codelab.gourmetsearchapp.view.onboarding

import android.util.SparseArray
import androidx.core.util.isEmpty
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import java.lang.ref.WeakReference

class OnboardingPagerAdapter(fm: FragmentManager, private val allOnboardings: List<Onboarding>) : FragmentStatePagerAdapter(fm) {

    private val fragments = SparseArray<WeakReference<OnboardingFragment>>()

    override fun getItem(position: Int): Fragment {
        val fragment = OnboardingFragment.newInstance(allOnboardings[position])

        if (fragments.isEmpty()) fragment.resumeAnimationAfterStart()

        fragments.put(position, WeakReference(fragment))

        return fragment
    }

    override fun getCount(): Int = allOnboardings.size

    fun getFragmentByPosition(position: Int): OnboardingFragment? {
        return if (fragments.size() > position) fragments[position]?.get() else null
    }
}


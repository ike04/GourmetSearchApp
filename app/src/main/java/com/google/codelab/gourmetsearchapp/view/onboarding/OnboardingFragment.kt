package com.google.codelab.gourmetsearchapp.view.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import autoCleared
import com.google.codelab.gourmetsearchapp.R
import com.google.codelab.gourmetsearchapp.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {
    companion object {
        private const val KEY_ONBOARDING = "onboarding"
        fun newInstance(onboarding: Onboarding): OnboardingFragment {
            return OnboardingFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_ONBOARDING, onboarding)
                }
            }
        }
    }
    private var binding: FragmentOnboardingBinding by autoCleared()
    private val onboarding: Onboarding
        get() = checkNotNull(arguments?.getSerializable(KEY_ONBOARDING) as Onboarding)
    private var resumeAnimationAfterStart = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingBinding.inflate(layoutInflater)
        binding.onboarding = onboarding
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (resumeAnimationAfterStart) {
            resumeAnimation()
            resumeAnimationAfterStart = false
        }
    }

    fun resumeAnimationAfterStart() {
        resumeAnimationAfterStart = true
    }

    fun resumeAnimation() {
        binding.animationView.resumeAnimation()
    }

    fun pauseAnimation() {
        binding.animationView.pauseAnimation()
    }

    fun isAnimating(): Boolean {
        return binding.animationView.isAnimating
    }
}

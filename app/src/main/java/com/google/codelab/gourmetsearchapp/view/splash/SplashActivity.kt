package com.google.codelab.gourmetsearchapp.view.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.AppLaunchChecker
import com.google.codelab.gourmetsearchapp.databinding.ActivitySplashBinding
import com.google.codelab.gourmetsearchapp.view.MainActivity
import com.google.codelab.gourmetsearchapp.view.onboarding.OnboardingActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val disposable = CompositeDisposable()

    companion object {
        const val FROM_SPLASH = "FROM_SPLASH"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        Observable
            .timer(2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                val intent = if (AppLaunchChecker.hasStartedFromLauncher(this)) {
                    Intent(this@SplashActivity, MainActivity::class.java)
                } else {
                    Intent(this@SplashActivity, OnboardingActivity::class.java)
                        .putExtra(FROM_SPLASH, true)
                }
                AppLaunchChecker.onActivityCreate(this)
                startActivity(intent)
                finish()
            }.addTo(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}

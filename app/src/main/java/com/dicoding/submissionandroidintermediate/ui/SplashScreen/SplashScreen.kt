package com.dicoding.submissionandroidintermediate.ui.SplashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.dicoding.submissionandroidintermediate.databinding.ActivitySplashScreenBinding
import com.dicoding.submissionandroidintermediate.ui.Auth.Login.LoginActivity
import com.dicoding.submissionandroidintermediate.ui.Onboarding.OnboardingActivity
import com.dicoding.submissionandroidintermediate.ui.ViewModelFactory

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private val splashScreenViewModel by viewModels<SplashScreenViewModel>() {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                splashScreenViewModel.getIsOnboardingUser().observe(this){isOnboarding ->
                    if(isOnboarding){
                        startActivity(Intent(this@SplashScreen, LoginActivity::class.java))
                    }else {
                        startActivity(Intent(this@SplashScreen, OnboardingActivity::class.java))
                    }
                }
                finish()
            }, DELAY_MILIS
        )
    }

    companion object{
        const val DELAY_MILIS = 1500L
    }
}
package com.dicoding.submissionandroidintermediate.ui.splashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import com.dicoding.submissionandroidintermediate.databinding.ActivitySplashScreenBinding
import com.dicoding.submissionandroidintermediate.ui.MainActivity
import com.dicoding.submissionandroidintermediate.ui.onboarding.OnboardingActivity
import com.dicoding.submissionandroidintermediate.ui.ViewModelFactory
import com.dicoding.submissionandroidintermediate.ui.auth.login.LoginActivity

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private val splashScreenViewModel by viewModels<SplashScreenViewModel>() {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splashScreenViewModel.getOnBoarding().observe(this){isOnboarding ->
            if(isOnboarding){
                splashScreenViewModel.getSessionUser().observe(this){
                    Log.d("SplashScreen", "onCreate: ${it.token}")
                   if(!it.token.isEmpty()){
                       Handler(Looper.getMainLooper()).postDelayed(
                           {
                               startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                               finish()
                           }, DELAY_MILIS
                       )
                   }else {
                       Handler(Looper.getMainLooper()).postDelayed(
                           {
                               startActivity(Intent(this@SplashScreen, LoginActivity::class.java))
                               finish()
                           }, DELAY_MILIS
                       )
                   }
                }
            }else {
                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        startActivity(Intent(this@SplashScreen, OnboardingActivity::class.java))
                        finish()
                    }, DELAY_MILIS
                )
            }
        }
    }

    companion object{
        const val DELAY_MILIS = 1500L
    }
}
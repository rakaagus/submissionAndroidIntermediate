package com.dicoding.submissionandroidintermediate.ui.SplashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.dicoding.submissionandroidintermediate.R
import com.dicoding.submissionandroidintermediate.databinding.ActivitySplashScreenBinding
import com.dicoding.submissionandroidintermediate.ui.MainActivity
import com.dicoding.submissionandroidintermediate.ui.Onboarding.OnboardingActivity

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                val intent = Intent(this@SplashScreen, OnboardingActivity::class.java)
                startActivity(intent)
                finish()
            }, DELAY_MILIS
        )
    }

    companion object{
        const val DELAY_MILIS = 1500L
    }
}
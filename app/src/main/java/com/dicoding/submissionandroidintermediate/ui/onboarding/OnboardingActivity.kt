package com.dicoding.submissionandroidintermediate.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.submissionandroidintermediate.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener{
            startActivity(Intent(this@OnboardingActivity, GetstartedActivity::class.java))
            finish()
        }

        binding.tvSkip.setOnClickListener{
            startActivity(Intent(this@OnboardingActivity, GetstartedActivity::class.java))
            finish()
        }
    }
}
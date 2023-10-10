package com.dicoding.submissionandroidintermediate.ui.Onboarding

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.dicoding.submissionandroidintermediate.databinding.ActivityGetstartedBinding
import com.dicoding.submissionandroidintermediate.ui.Auth.Login.LoginActivity
import com.dicoding.submissionandroidintermediate.ui.ViewModelFactory

class GetstartedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGetstartedBinding
    private val getStartedViewModel by viewModels<GetStartedViewModel> {
        ViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetstartedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGetStarted.setOnClickListener {
            getStartedViewModel.saveIsOnboarding(true)
            startActivity(Intent(this@GetstartedActivity, LoginActivity::class.java))
            finish()
        }

        startAnimation()
    }

    private fun startAnimation() {
        val ivGetStarted = ObjectAnimator.ofFloat(binding.ivGetStarted, View.ALPHA, 1f).setDuration(500)
        val tvSubTitle = ObjectAnimator.ofFloat(binding.tvSubtitleGetStarted, View.ALPHA, 1f).setDuration(500)
        val tvTitle = ObjectAnimator.ofFloat(binding.tvTitleGetStarted, View.ALPHA, 1f).setDuration(500)
        val btnGetStarted = ObjectAnimator.ofFloat(binding.btnGetStarted, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(ivGetStarted, tvSubTitle, tvTitle, btnGetStarted)
            startDelay = 250
        }.start()
    }
}
package com.dicoding.submissionandroidintermediate.ui.Onboarding

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dicoding.submissionandroidintermediate.databinding.ActivityGetstartedBinding

class GetstartedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGetstartedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetstartedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startAnimation()
    }

    private fun startAnimation() {
        val ivGetStarted = ObjectAnimator.ofFloat(binding.ivGetStarted, View.ALPHA, 1f).setDuration(500)
        val tvSubTitle = ObjectAnimator.ofFloat(binding.tvSubtitleGetStarted, View.ALPHA, 1f).setDuration(500)
        val tvTitle = ObjectAnimator.ofFloat(binding.tvTitleGetStarted, View.ALPHA, 1f).setDuration(500)
        val btnGetStarted = ObjectAnimator.ofFloat(binding.btnNext, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(ivGetStarted, tvSubTitle, tvTitle, btnGetStarted)
            startDelay = 250
        }.start()
    }
}
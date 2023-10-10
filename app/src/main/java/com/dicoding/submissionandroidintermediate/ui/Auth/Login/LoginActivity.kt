package com.dicoding.submissionandroidintermediate.ui.Auth.Login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dicoding.submissionandroidintermediate.R
import com.dicoding.submissionandroidintermediate.databinding.ActivityLoginBinding
import com.dicoding.submissionandroidintermediate.ui.Auth.Register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }

        startAnimation()
    }

    private fun startAnimation() {
        val imgLogin = ObjectAnimator.ofFloat(binding.imgViewLogin, View.ALPHA, 1F).setDuration(350)
        val titleLogin = ObjectAnimator.ofFloat(binding.tvTitleLogin, View.ALPHA, 1F).setDuration(350)
        val descLogin = ObjectAnimator.ofFloat(binding.tvDescLogin, View.ALPHA, 1F).setDuration(350)
        val emailEdtLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1F).setDuration(350)
        val passwordLayout = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1F).setDuration(350)
        val tvForgot = ObjectAnimator.ofFloat(binding.tvForgotPass, View.ALPHA, 1F).setDuration(350)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin,View.ALPHA, 1F).setDuration(350)
        val linearText = ObjectAnimator.ofFloat(binding.linearBottomBtn, View.ALPHA, 1F).setDuration(350)

        AnimatorSet().apply {
            playSequentially(
                imgLogin,
                titleLogin,
                descLogin,
                emailEdtLayout,
                passwordLayout,
                tvForgot,
                btnLogin,
                linearText
            )
            startDelay = 150
        }.start()
    }
}
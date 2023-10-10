package com.dicoding.submissionandroidintermediate.ui.Auth.Register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dicoding.submissionandroidintermediate.R
import com.dicoding.submissionandroidintermediate.databinding.ActivityRegisterBinding
import com.dicoding.submissionandroidintermediate.ui.Auth.Login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvSignIn.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        }

        startAnimation()

    }

    private fun startAnimation() {
        val imgRegister = ObjectAnimator.ofFloat(binding.imgViewRegister, View.ALPHA, 1F).setDuration(350)
        val titleRegister = ObjectAnimator.ofFloat(binding.tvTitleRegister, View.ALPHA, 1F).setDuration(350)
        val descRegister = ObjectAnimator.ofFloat(binding.tvDescRegister, View.ALPHA, 1F).setDuration(350)
        val emailEdtLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1F).setDuration(350)
        val passwordLayout = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1F).setDuration(350)
        val nameLayout = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1F).setDuration(350)
        val btnRegister= ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1F).setDuration(350)
        val linearText = ObjectAnimator.ofFloat(binding.linearBottomBtnRegister, View.ALPHA, 1F).setDuration(350)

        AnimatorSet().apply {
            playSequentially(
                imgRegister,
                titleRegister,
                descRegister,
                nameLayout,
                emailEdtLayout,
                passwordLayout,
                btnRegister,
                linearText
            )
            startDelay = 150
        }.start()
    }
}
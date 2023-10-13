package com.dicoding.submissionandroidintermediate.ui.auth.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.submissionandroidintermediate.R
import com.dicoding.submissionandroidintermediate.data.Result
import com.dicoding.submissionandroidintermediate.databinding.ActivityLoginBinding
import com.dicoding.submissionandroidintermediate.ui.auth.register.RegisterActivity
import com.dicoding.submissionandroidintermediate.ui.MainActivity
import com.dicoding.submissionandroidintermediate.ui.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loadingAuth: Dialog
    private val loginViewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingAuth = Dialog(this)

        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }

        startAnimation()
        processLogin()
    }

    private fun processLogin() {
        binding.btnLogin.setOnClickListener {
           if(binding.emailEditText.text.toString().isEmpty()) {
                   Snackbar.make(it, getString(R.string.text_error_email), Snackbar.LENGTH_SHORT).show()
           }else {
               val email = binding.emailEditText.text.toString()
               val password = binding.passwordEditText.text.toString()
               userLogin(email, password)
           }
        }
    }

    private fun userLogin(email: String, password: String) {
        loginViewModel.login(email, password).observe(this){result->
            if(result != null){
                when(result){
                    is Result.Loading -> {
                        showDialog()
                    }
                    is Result.Success -> {
                        dismissLoading()
                        Log.d("LoginActivity", "processLogin: ${result.data}")
                        AlertDialog.Builder(this).apply {
                            setTitle(getString(R.string.text_title_success_login))
                            setMessage(getString(R.string.text_desc_login))
                            setPositiveButton(getString(R.string.text_button_success_login)) { _, _ ->
                                val intent = Intent(context, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                            create()
                            show()
                        }
                    }
                    is Result.Error -> {
                        dismissLoading()
                        Toast.makeText(
                            this@LoginActivity,
                            getString(R.string.text_error_api, result.error),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun dismissLoading() {
        if(loadingAuth.isShowing){
            loadingAuth.dismiss()
        }
    }

    private fun showDialog() {
        loadingAuth.setContentView(R.layout.bg_loading_auth)
        loadingAuth.setCancelable(false)
        loadingAuth.setCanceledOnTouchOutside(false)
        loadingAuth.show()
    }

    private fun startAnimation() {
        val imgLogin = ObjectAnimator.ofFloat(binding.imgViewLogin, View.ALPHA, 1F).setDuration(150)
        val titleLogin = ObjectAnimator.ofFloat(binding.tvTitleLogin, View.ALPHA, 1F).setDuration(150)
        val descLogin = ObjectAnimator.ofFloat(binding.tvDescLogin, View.ALPHA, 1F).setDuration(150)
        val emailEdtLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1F).setDuration(150)
        val passwordLayout = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1F).setDuration(150)
        val tvForgot = ObjectAnimator.ofFloat(binding.tvForgotPass, View.ALPHA, 1F).setDuration(150)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin,View.ALPHA, 1F).setDuration(150)
        val linearText = ObjectAnimator.ofFloat(binding.linearBottomBtn, View.ALPHA, 1F).setDuration(150)

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
            startDelay = 100
        }.start()
    }
}
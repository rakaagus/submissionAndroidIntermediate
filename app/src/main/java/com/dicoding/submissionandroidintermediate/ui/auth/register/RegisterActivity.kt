package com.dicoding.submissionandroidintermediate.ui.auth.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.dicoding.submissionandroidintermediate.R
import com.dicoding.submissionandroidintermediate.data.Result
import com.dicoding.submissionandroidintermediate.databinding.ActivityRegisterBinding
import com.dicoding.submissionandroidintermediate.ui.auth.login.LoginActivity
import com.dicoding.submissionandroidintermediate.ui.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var loadingAuth: Dialog
    private val registerViewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingAuth = Dialog(this)

        binding.tvSignIn.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        }

        startAnimation()
        processRegister()
    }

    private fun processRegister() {
        binding.btnRegister.setOnClickListener {
            if(binding.emailEditText.toString().isEmpty() || binding.nameEditText.toString().isEmpty()){
                Snackbar.make(it, getString(R.string.text_error_email), Snackbar.LENGTH_SHORT).show()
            }else {
                val email = binding.emailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()
                val name = binding.nameEditText.text.toString()
                userRegister(name, email, password)
            }
        }
    }

    private fun userRegister(name: String, email: String, password: String) {
        registerViewModel.register(
            name,
            email,
            password
        ).observe(this) { result->
            if(result != null){
                when(result){
                    is Result.Loading -> {
                        showDialog()
                    }
                    is Result.Success -> {
                        dismissLoading()
                        Log.d("RegisterActivity", "processRegister: ${result.data}")
                        AlertDialog.Builder(this).apply {
                            setTitle(getString(R.string.text_title_success_login))
                            setMessage(getString(R.string.text_desc_login))
                            setPositiveButton(getString(R.string.text_button_success_login)) { _, _ ->
                                val intent = Intent(context, LoginActivity::class.java)
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
                            this@RegisterActivity,
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
        val imgRegister = ObjectAnimator.ofFloat(binding.imgViewRegister, View.ALPHA, 1F).setDuration(150)
        val titleRegister = ObjectAnimator.ofFloat(binding.tvTitleRegister, View.ALPHA, 1F).setDuration(150)
        val descRegister = ObjectAnimator.ofFloat(binding.tvDescRegister, View.ALPHA, 1F).setDuration(150)
        val emailEdtLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1F).setDuration(150)
        val passwordLayout = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1F).setDuration(150)
        val nameLayout = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1F).setDuration(150)
        val btnRegister= ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1F).setDuration(150)
        val linearText = ObjectAnimator.ofFloat(binding.linearBottomBtnRegister, View.ALPHA, 1F).setDuration(150)

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
            startDelay = 100
        }.start()
    }
}
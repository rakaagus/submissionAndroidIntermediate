package com.dicoding.submissionandroidintermediate.ui.auth.login

import androidx.lifecycle.ViewModel
import com.dicoding.submissionandroidintermediate.data.AppRepository

class LoginViewModel(private val repository: AppRepository): ViewModel() {

    fun login(email: String, password: String) = repository.loginUser(email, password)

}
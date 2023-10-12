package com.dicoding.submissionandroidintermediate.ui.auth.register

import androidx.lifecycle.ViewModel
import com.dicoding.submissionandroidintermediate.data.AppRepository

class RegisterViewModel(private val repository: AppRepository): ViewModel() {
    fun register(name: String, email: String, password: String) = repository.registerUser(name, email, password)
}
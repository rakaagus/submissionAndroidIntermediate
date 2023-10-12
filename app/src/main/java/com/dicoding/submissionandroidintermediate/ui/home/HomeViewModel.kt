package com.dicoding.submissionandroidintermediate.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.submissionandroidintermediate.data.AppRepository

class HomeViewModel(private val repository: AppRepository): ViewModel() {

    fun getAllStory(token: String) = repository.getAllStory(token)

    fun getUserSession() = repository.getSessionUser().asLiveData()

}
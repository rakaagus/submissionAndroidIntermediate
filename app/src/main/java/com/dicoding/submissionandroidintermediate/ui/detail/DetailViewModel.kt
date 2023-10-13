package com.dicoding.submissionandroidintermediate.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.submissionandroidintermediate.data.AppRepository

class DetailViewModel(private val repository: AppRepository): ViewModel() {

    fun getDetail(token: String, id: String) = repository.getDetailStory(token, id)

    fun getTokenUser() = repository.getSessionUser().asLiveData()
}
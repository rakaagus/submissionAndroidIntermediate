package com.dicoding.submissionandroidintermediate.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.submissionandroidintermediate.data.AppRepository
import java.io.File

class AddPostViewModel(private val repository: AppRepository): ViewModel() {
    fun uploadStory(token:String, file: File, description: String) = repository.uploadStory(token, file, description)

    fun getUserSession() = repository.getSessionUser().asLiveData()
}
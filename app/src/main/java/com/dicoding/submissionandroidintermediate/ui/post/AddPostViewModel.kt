package com.dicoding.submissionandroidintermediate.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.submissionandroidintermediate.data.AppRepository
import okhttp3.RequestBody
import java.io.File

class AddPostViewModel(private val repository: AppRepository): ViewModel() {
    fun uploadStory(
        token:String,
        file: File,
        description: String,
        lat: String?,
        lon: String?
    ) = repository.uploadStory(token, file, description, lat, lon)

    fun getUserSession() = repository.getSessionUser().asLiveData()
}
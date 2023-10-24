package com.dicoding.submissionandroidintermediate.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.submissionandroidintermediate.data.AppRepository
import kotlinx.coroutines.launch

class MapsActivityViewModel(private val repository: AppRepository): ViewModel() {

    fun getAllStoriesWithLocation(token: String) = repository.getAllStoryWithLocation(token)

    fun getUserSession() = repository.getSessionUser().asLiveData()


}
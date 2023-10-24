package com.dicoding.submissionandroidintermediate.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.dicoding.submissionandroidintermediate.data.AppRepository
import com.dicoding.submissionandroidintermediate.data.local.entity.StoryEntity

class HomeViewModel(private val repository: AppRepository): ViewModel() {

    fun getAllStory(token: String): LiveData<PagingData<StoryEntity>> = repository.getStoryWithPaging(token).cachedIn(viewModelScope)

    fun getUserSession() = repository.getSessionUser().asLiveData()

}
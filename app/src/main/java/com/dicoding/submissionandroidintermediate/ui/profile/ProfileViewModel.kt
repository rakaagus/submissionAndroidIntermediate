package com.dicoding.submissionandroidintermediate.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.submissionandroidintermediate.data.AppRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: AppRepository): ViewModel(){

    fun getSessionUser() = repository.getSessionUser().asLiveData()
    suspend fun clearSession(){
        viewModelScope.launch {
            repository.clearSessionUser()
        }
    }
}
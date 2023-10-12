package com.dicoding.submissionandroidintermediate.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.submissionandroidintermediate.data.AppRepository
import kotlinx.coroutines.launch

class GetStartedViewModel(private val repository: AppRepository): ViewModel() {
    fun saveIsOnboarding(isOnboarding: Boolean){
        viewModelScope.launch {
            repository.saveOnBoarding(isOnboarding)
        }
    }
}
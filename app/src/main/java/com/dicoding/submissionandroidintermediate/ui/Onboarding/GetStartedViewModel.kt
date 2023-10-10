package com.dicoding.submissionandroidintermediate.ui.Onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.submissionandroidintermediate.data.AppPreferences
import kotlinx.coroutines.launch

class GetStartedViewModel(private val pref: AppPreferences): ViewModel() {
    fun saveIsOnboarding(isOnboarding: Boolean){
        viewModelScope.launch {
            pref.saveIsOnboardingUser(isOnboarding)
        }
    }
}
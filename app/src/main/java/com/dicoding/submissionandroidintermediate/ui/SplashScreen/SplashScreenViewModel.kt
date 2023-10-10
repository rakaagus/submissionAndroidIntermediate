package com.dicoding.submissionandroidintermediate.ui.SplashScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.submissionandroidintermediate.data.AppPreferences

class SplashScreenViewModel(private val pref: AppPreferences): ViewModel() {
    fun getIsOnboardingUser(): LiveData<Boolean> = pref.getIsOnBoardingUser().asLiveData()
}
package com.dicoding.submissionandroidintermediate.ui.splashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.submissionandroidintermediate.data.AppRepository

class SplashScreenViewModel(private val repository: AppRepository): ViewModel() {

    fun getOnBoarding() = repository.onBoarding().asLiveData()

    fun getSessionUser() = repository.getSessionUser().asLiveData()

}
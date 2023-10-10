package com.dicoding.submissionandroidintermediate.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submissionandroidintermediate.data.AppPreferences
import com.dicoding.submissionandroidintermediate.data.datastore
import com.dicoding.submissionandroidintermediate.ui.Onboarding.GetStartedViewModel
import com.dicoding.submissionandroidintermediate.ui.SplashScreen.SplashScreenViewModel

class ViewModelFactory private constructor(
    private val preferences: AppPreferences
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SplashScreenViewModel::class.java)){
            return SplashScreenViewModel(preferences) as T
        }else if(modelClass.isAssignableFrom(GetStartedViewModel::class.java)){
            return GetStartedViewModel(preferences) as T
        }
        throw IllegalArgumentException("Unknow ViewModel Class"+ modelClass)
    }

    companion object{
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context):ViewModelFactory =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: ViewModelFactory(AppPreferences.getInstance(context.datastore))
            }.also { INSTANCE = it }
    }
}
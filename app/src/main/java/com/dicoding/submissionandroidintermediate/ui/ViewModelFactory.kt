package com.dicoding.submissionandroidintermediate.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submissionandroidintermediate.data.AppRepository
import com.dicoding.submissionandroidintermediate.di.Injection
import com.dicoding.submissionandroidintermediate.ui.auth.login.LoginViewModel
import com.dicoding.submissionandroidintermediate.ui.auth.register.RegisterViewModel
import com.dicoding.submissionandroidintermediate.ui.home.HomeViewModel
import com.dicoding.submissionandroidintermediate.ui.onboarding.GetStartedViewModel
import com.dicoding.submissionandroidintermediate.ui.profile.ProfileViewModel
import com.dicoding.submissionandroidintermediate.ui.splashScreen.SplashScreenViewModel

class ViewModelFactory private constructor(
    private val appRepository: AppRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SplashScreenViewModel::class.java)){
            return SplashScreenViewModel(appRepository) as T
        }else if(modelClass.isAssignableFrom(GetStartedViewModel::class.java)){
            return GetStartedViewModel(appRepository) as T
        }else if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(appRepository) as T
        }else if(modelClass.isAssignableFrom(RegisterViewModel::class.java)){
            return RegisterViewModel(appRepository) as T
        }else if(modelClass.isAssignableFrom(ProfileViewModel::class.java)){
            return ProfileViewModel(appRepository) as T
        }else if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(appRepository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel Class"+ modelClass)
    }

    companion object{
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context):ViewModelFactory =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: ViewModelFactory(Injection.providerRepository(context))
            }.also { INSTANCE = it }
    }
}
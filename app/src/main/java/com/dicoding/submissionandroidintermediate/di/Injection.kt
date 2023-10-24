package com.dicoding.submissionandroidintermediate.di

import android.content.Context
import com.dicoding.submissionandroidintermediate.data.AppPreferences
import com.dicoding.submissionandroidintermediate.data.AppRepository
import com.dicoding.submissionandroidintermediate.data.AuthPreferences
import com.dicoding.submissionandroidintermediate.data.appDataStore
import com.dicoding.submissionandroidintermediate.data.authDataStore
import com.dicoding.submissionandroidintermediate.data.local.room.RemoteKeysDao
import com.dicoding.submissionandroidintermediate.data.local.room.StoryDatabase
import com.dicoding.submissionandroidintermediate.data.remote.ApiConfig

object Injection {
    fun providerRepository(context: Context): AppRepository{
        val database = StoryDatabase.getInstance(context)
        val prefApp = AppPreferences.getInstance(context.appDataStore)
        val prefAuth = AuthPreferences.getInstance(context.authDataStore)
        val apiService = ApiConfig.getService()
        return AppRepository(
            apiService = apiService,
            database = database,
            prefApp = prefApp,
            prefAuth = prefAuth
        )
    }
}
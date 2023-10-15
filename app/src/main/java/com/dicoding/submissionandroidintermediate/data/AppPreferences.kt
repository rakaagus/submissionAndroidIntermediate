package com.dicoding.submissionandroidintermediate.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.appDataStore: DataStore<Preferences> by preferencesDataStore(name = "AppSetting")
class AppPreferences private constructor(private val appDataStore: DataStore<Preferences>){
    private val isOnboarding = booleanPreferencesKey(IS_ONBOARDING_KEY)

    fun getIsOnBoardingUser(): Flow<Boolean> {
        return appDataStore.data.map { preferences ->
            preferences[isOnboarding] ?: false
        }
    }

    suspend fun saveIsOnboardingUser(isOnboarding: Boolean){
        appDataStore.edit { preferences ->
            preferences[this.isOnboarding] = isOnboarding
        }
    }

    companion object{
        @Volatile
        private var INSTANCE: AppPreferences? = null

        // keys
        private const val IS_ONBOARDING_KEY = "isOnboardingKey"

        fun getInstance(dataStore: DataStore<Preferences>): AppPreferences{
            return INSTANCE ?: synchronized(this){
                val instance = AppPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
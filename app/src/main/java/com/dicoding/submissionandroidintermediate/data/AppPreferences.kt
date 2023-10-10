package com.dicoding.submissionandroidintermediate.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "AppSetting")
class AppPreferences private constructor(private val dataStore: DataStore<Preferences>){
    private val isOnboarding = booleanPreferencesKey(IS_ONBOARDING_KEY)
    private val userToken = stringPreferencesKey(TOKEN_USER_KEY)
    private val userName = stringPreferencesKey(USER_NAME_KEY)
    private val emailUser = stringPreferencesKey(EMAIL_USER_KEY)

    fun getIsOnBoardingUser(): Flow<Boolean> {
        return dataStore.data.map {preferences ->
            preferences[isOnboarding] ?: false
        }
    }

    suspend fun saveIsOnboardingUser(isOnboarding: Boolean){
        dataStore.edit { preferences ->
            preferences[this.isOnboarding] = isOnboarding
        }
    }

    companion object{
        @Volatile
        private var INSTANCE: AppPreferences? = null

        // keys
        private const val IS_ONBOARDING_KEY = "isOnboardingKey"
        private const val TOKEN_USER_KEY = "tokenUserKey"
        private const val USER_NAME_KEY = "userNameKey"
        private const val EMAIL_USER_KEY = "emailUserKey"

        fun getInstance(dataStore: DataStore<Preferences>): AppPreferences{
            return INSTANCE ?: synchronized(this){
                val instance = AppPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
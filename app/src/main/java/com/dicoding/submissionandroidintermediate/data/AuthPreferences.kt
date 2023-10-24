package com.dicoding.submissionandroidintermediate.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dicoding.submissionandroidintermediate.data.local.entity.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(name = "AuthPreferences")
class AuthPreferences private constructor(private val authDataStore: DataStore<Preferences>){
    private val userToken = stringPreferencesKey(TOKEN_USER_KEY)
    private val userName = stringPreferencesKey(USER_NAME_KEY)
    private val userUid = stringPreferencesKey(UID_USER_KEY)

    fun getSessionUser(): Flow<User>{
        return authDataStore.data.map {pref->
            User(
                id = pref[userUid] ?: "",
                name = pref[userName] ?: "",
                token = pref[userToken] ?: ""
            )
        }
    }

    suspend fun saveLoginSession(user: User){
        authDataStore.edit {pref->
            pref[this.userToken] = user.token
            pref[this.userName] = user.name
            pref[this.userUid] = user.id
        }
    }

    suspend fun clearTokenSession(){
        authDataStore.edit {preferences ->
            preferences.clear()
        }
    }


    companion object{
        private const val TOKEN_USER_KEY = "tokenUserKey"
        private const val USER_NAME_KEY = "userNameKey"
        private const val UID_USER_KEY = "uidUserKey"

        private var INSTANCE: AuthPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): AuthPreferences{
            return INSTANCE ?: synchronized(this){
                val instance = AuthPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
package com.dicoding.submissionandroidintermediate.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.dicoding.submissionandroidintermediate.data.local.entity.StoryEntity
import com.dicoding.submissionandroidintermediate.data.local.entity.User
import com.dicoding.submissionandroidintermediate.data.local.room.StoryDao
import com.dicoding.submissionandroidintermediate.data.remote.ApiService
import com.dicoding.submissionandroidintermediate.data.remote.response.DetailStoryResponse
import com.dicoding.submissionandroidintermediate.data.remote.response.LoginResponse
import com.dicoding.submissionandroidintermediate.data.remote.response.RegisterResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class AppRepository(
    private val apiService: ApiService,
    private val storyDao: StoryDao,
    private val prefApp: AppPreferences,
    private val prefAuth: AuthPreferences
) {

    /*Logic Api*/
    fun loginUser(email: String, password: String): LiveData<Result<LoginResponse>> = liveData{
        emit(Result.Loading)
        try {
            val response = apiService.login(email, password)
            val error = response.error
            val result = response.loginResult
            if (!error){
                prefAuth.clearTokenSession()
                val user = User(
                    id = result.userId,
                    name = result.name,
                    token = result.token
                )
                prefAuth.saveLoginSession(user)
                emit(Result.Success(response))
            }
        } catch (e: Exception){
            Log.d("AppRepository", "loginUser: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun registerUser(name: String, email: String, password: String): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(
                name = name,
                email = email,
                password = password
            )
            val error = response.error
            if (!error){
                emit(Result.Success(response))
            }else {
                emit(Result.Error(response.message))
            }
        }catch (e: Exception){
            Log.d("AppRepository", "registerUser: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getAllStory(token: String): LiveData<Result<List<StoryEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getAllStory(
                setGenerateToken(token)
            )
            val error = response.error
            if(!error){
                val listStory = response.listStory
                val newStory = listStory.map {story->
                    StoryEntity(
                        story.id,
                        story.name,
                        story.description,
                        story.photoUrl,
                        story.createdAt,
                    )
                }
                storyDao.deleteStory()
                storyDao.insertStory(newStory)
            }else {
                emit(Result.Error(response.message))
            }
        }catch (e: Exception){
            Log.d("AppRepository", "getAllStory: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<StoryEntity>>> = storyDao.getAllStory().map { Result.Success(it) }
        emitSource(localData)
    }

    fun uploadStory(imageFile: File, description: String) = liveData {
        emit(Result.Loading)
        val requestBody = description.toRequestBody("text/plain".toMediaType())
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "photo",
            imageFile.name,
            requestImageFile
        )
        try {
            val successResponse = apiService.uploadStory(
                multipartBody,
                requestBody
            )
            emit(Result.Success(successResponse))
        }catch (e: HttpException){
            val errorBody = e.response()?.errorBody().toString()
            val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
            emit(Result.Error(errorResponse.message))
        }
    }

    fun getDetailStory(
        id: String
    ): LiveData<Result<DetailStoryResponse>> = liveData {
        emit(Result.Loading)
        try {

        }catch (e: Exception){

        }
    }


    /*Logic For DataStore Preferences*/
    fun getSessionUser(): Flow<User> = prefAuth.getSessionUser()

    suspend fun clearSessionUser() {
        prefAuth.clearTokenSession()
    }

    fun onBoarding(): Flow<Boolean> = prefApp.getIsOnBoardingUser()

    suspend fun saveOnBoarding(isOnboarding: Boolean){
        prefApp.saveIsOnboardingUser(isOnboarding)
    }

    fun setGenerateToken(token: String): String = "Bearer $token"

    companion object{
        private var INSTANCE: AppRepository? = null
        fun getInstance(
            apiService: ApiService,
            storyDao: StoryDao,
            prefApp: AppPreferences,
            prefAuth: AuthPreferences
        ) : AppRepository = INSTANCE ?: synchronized(this){
            INSTANCE ?: AppRepository(
                apiService,
                storyDao,
                prefApp,
                prefAuth
            )
        }.also { INSTANCE = it }
    }
}
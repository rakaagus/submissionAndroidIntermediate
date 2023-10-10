package com.dicoding.submissionandroidintermediate.data.remote.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(
	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

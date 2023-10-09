package com.dicoding.submissionandroidintermediate.ui.Post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.submissionandroidintermediate.R

class AddPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)
    }

    companion object{
        const val TYPE_POST = "type_post"
        private const val TAG = "addPostActivity"
    }
}
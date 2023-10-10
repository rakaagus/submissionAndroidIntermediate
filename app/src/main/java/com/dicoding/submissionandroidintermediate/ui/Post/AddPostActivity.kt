package com.dicoding.submissionandroidintermediate.ui.Post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.submissionandroidintermediate.R
import com.dicoding.submissionandroidintermediate.databinding.ActivityAddPostBinding

class AddPostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    companion object{
        const val TYPE_POST = "type_post"
        private const val TAG = "addPostActivity"
    }
}
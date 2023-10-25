package com.dicoding.submissionandroidintermediate.ui.detail

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.dicoding.submissionandroidintermediate.R
import com.dicoding.submissionandroidintermediate.databinding.ActivityDetailBinding
import com.dicoding.submissionandroidintermediate.ui.ViewModelFactory
import com.dicoding.submissionandroidintermediate.data.Result
import com.dicoding.submissionandroidintermediate.data.local.entity.StoryEntity
import com.dicoding.submissionandroidintermediate.data.remote.response.DetailStoryResponse
import com.dicoding.submissionandroidintermediate.utils.withDateFormat

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var loadingDetail: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingDetail = Dialog(this)

        val story = intent.getParcelableExtra<StoryEntity>(ID_STORY_KEY)

        if(story != null){
            setupDetailStory(story)
        }

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun setupDetailStory(data: StoryEntity) {
        val story = data
        Glide.with(this)
            .load(story.photoUrl)
            .centerCrop()
            .into(binding.ivImageStory)
        binding.apply {
            tvDate.text = story.createdAt.withDateFormat()
            tvDescription.text = story.description
            tvNameUser.text = story.name
        }
    }

    private fun showLoading() {
        loadingDetail.setContentView(R.layout.bg_loading_auth)
        loadingDetail.setCancelable(false)
        loadingDetail.setCanceledOnTouchOutside(false)
        loadingDetail.show()
    }

    private fun dismisLoading(){
        if (loadingDetail.isShowing){
            loadingDetail.dismiss()
        }
    }

    companion object{
        const val ID_STORY_KEY = "ID_STORY_KEY"
    }
}
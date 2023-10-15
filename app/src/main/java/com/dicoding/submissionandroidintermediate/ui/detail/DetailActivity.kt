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
import com.dicoding.submissionandroidintermediate.data.remote.response.DetailStoryResponse

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var loadingDetail: Dialog
    private val detailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingDetail = Dialog(this)

        val idStory = intent.getStringExtra(ID_STORY_KEY)

        binding.ivBack.setOnClickListener {
            finish()
        }

        detailViewModel.getTokenUser().observe(this){user->
            detailViewModel.getDetail(user.token, idStory.toString()).observe(this){result->
                if(result != null){
                    when(result){
                        is Result.Loading -> {
                            showLoading()
                        }
                        is Result.Success -> {
                            dismisLoading()
                            setupDetailStory(result.data)
                        }
                        is Result.Error -> {
                            dismisLoading()
                            Log.d("DetailActivity", "onCreate: ${result.error}")
                        }
                    }
                }
            }
        }

    }

    private fun setupDetailStory(data: DetailStoryResponse) {
        val story = data.story
        if(story.id.isNotEmpty()){
            binding.apply {
                consAvatarName.visibility = View.VISIBLE
                ivImageStory.visibility = View.VISIBLE
                tvDate.visibility = View.VISIBLE
                tvDescription.visibility = View.VISIBLE
            }
        }else {
            binding.apply {
                consAvatarName.visibility = View.GONE
                ivImageStory.visibility = View.GONE
                tvDate.visibility = View.GONE
                tvDescription.visibility = View.GONE
            }
        }
        Glide.with(this)
            .load(story.photoUrl)
            .centerCrop()
            .into(binding.ivImageStory)
        binding.apply {
            tvDate.text = story.createdAt
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
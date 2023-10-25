package com.dicoding.submissionandroidintermediate.ui.home.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submissionandroidintermediate.data.local.entity.StoryEntity
import com.dicoding.submissionandroidintermediate.databinding.ItemStoryBinding
import com.dicoding.submissionandroidintermediate.ui.detail.DetailActivity
import com.dicoding.submissionandroidintermediate.utils.withDateFormat

class StoryAdapter: PagingDataAdapter<StoryEntity, StoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallBack: OnClickCallback
    class MyViewHolder(val binding: ItemStoryBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val storyItem = getItem(position)
        Glide.with(holder.binding.root)
            .load(storyItem?.photoUrl)
            .centerCrop()
            .into(holder.binding.ivImageStory)
        holder.binding.tvNameUser.text = storyItem?.name
        holder.binding.tvDate.text = storyItem?.createdAt?.withDateFormat()
        holder.binding.tvDescription.text = storyItem?.description
        holder.binding.consParentClick.setOnClickListener {
            val context = holder.binding
            val optionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    context.root.context as Activity,
                    Pair(context.ivImageStory, "story_image"),
                    Pair(context.tvDescription, "story_desc"),
                    Pair(context.consAvatarName, "story_avatar")
                )
            Intent(context.root.context, DetailActivity::class.java).also {intent->
                intent.putExtra(DetailActivity.ID_STORY_KEY, storyItem)
                context.root.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnClickCallback){
        this.onItemClickCallBack = onItemClickCallback
    }

    interface OnClickCallback{
        fun onItemClicked(data: StoryEntity)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<StoryEntity> =
            object : DiffUtil.ItemCallback<StoryEntity>() {
                override fun areItemsTheSame(oldItem: StoryEntity, newItem: StoryEntity): Boolean {
                    return oldItem == newItem
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: StoryEntity, newItem: StoryEntity): Boolean {
                    return oldItem.id == newItem.id
                }
            }
    }
}
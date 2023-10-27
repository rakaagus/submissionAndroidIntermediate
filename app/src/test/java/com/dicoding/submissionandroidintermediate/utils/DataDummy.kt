package com.dicoding.submissionandroidintermediate.utils

import com.dicoding.submissionandroidintermediate.data.local.entity.StoryEntity
import com.dicoding.submissionandroidintermediate.data.remote.response.ListStoryItem
import com.dicoding.submissionandroidintermediate.data.remote.response.StoryResponse

object DataDummy {

    fun generateDummyStoryResponse(): StoryResponse{
        val error = false
        val message = "Stories fetched successfully"
        val listStory = mutableListOf<ListStoryItem>()

        for(i in 1..30){
            val story = ListStoryItem(
                id = "story-nUHrmz6c9_bjTqAg",
                name = "student89",
                description = "tesss",
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1698377010160_jBNxxqlT.jpg",
                createdAt = "2023-10-27T03:23:30.162Z",
                lat = null,
                lon = null
            )
            listStory.add(story)
        }

        return StoryResponse(
            listStory = listStory,
            error = error,
            message = message
        )
    }

    fun generateDummyStoryEntity():List<StoryEntity>{
        val storyEntity = ArrayList<StoryEntity>()
        for (i in 1..30){
            val story = StoryEntity(
                id = "story-nUHrmz6c9_bjTqAg",
                name = "student89",
                description = "tesss",
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1698377010160_jBNxxqlT.jpg",
                createdAt = "2023-10-27T03:23:30.162Z",
                lat = null,
                lon = null
            )
            storyEntity.add(story)
        }

        return storyEntity
    }

}
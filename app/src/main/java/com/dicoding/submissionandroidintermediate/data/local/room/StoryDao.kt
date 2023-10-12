package com.dicoding.submissionandroidintermediate.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.submissionandroidintermediate.data.local.entity.StoryEntity

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStory(story: List<StoryEntity>)

    @Query("SELECT * FROM story order by createdAt DESC")
    fun getAllStory(): LiveData<List<StoryEntity>>

    @Query("DELETE FROM story")
    suspend fun deleteStory()
}
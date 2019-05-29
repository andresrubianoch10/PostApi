package com.arubianoch.posttest.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arubianoch.posttest.data.network.response.Post

/**
 * @author Andres Rubiano Del Chiaro
 */
@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun upsert(post: List<Post>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(post: Post)

    @Query("SELECT * FROM post")
    fun getPost(): LiveData<List<Post>>

    @Query("SELECT * FROM post WHERE isFavorite = 1")
    fun getFavoritePost(): LiveData<List<Post>>

    @Query("DELETE FROM post")
    fun deleteAllPost()

    @Query("UPDATE post SET isFavorite = :favorite WHERE id = :postId")
    fun updatePostAsFavorite(postId: String, favorite: String)

    @Query("DELETE FROM post WHERE id = :postId")
    fun deletePostById(postId: String)
}
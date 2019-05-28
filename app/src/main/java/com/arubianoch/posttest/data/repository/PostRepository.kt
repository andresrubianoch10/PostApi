package com.arubianoch.posttest.data.repository

import androidx.lifecycle.LiveData
import com.arubianoch.posttest.data.network.response.Comment
import com.arubianoch.posttest.data.network.response.Post

/**
 * @author Andres Rubiano Del Chiaro
 */
interface PostRepository {

    suspend fun getPosts(): LiveData<List<Post>>

    suspend fun getFavoritePost(): LiveData<List<Post>>

    suspend fun insertPost(postUpdated: Post)

    suspend fun fetchPosts()

    suspend fun updatePostAsFavorite(postId: String, isFavorite: Boolean)

    suspend fun deletePostById(postId: String)

    suspend fun getComments(postId: String): LiveData<List<Comment>>
}
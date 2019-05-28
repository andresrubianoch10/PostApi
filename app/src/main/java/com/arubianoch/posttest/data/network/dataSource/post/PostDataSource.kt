package com.arubianoch.posttest.data.network.dataSource.post

import androidx.lifecycle.LiveData
import com.arubianoch.posttest.data.network.response.Post

/**
 * @author Andres Rubiano Del Chiaro
 */
interface PostDataSource {
    val downloadedPost: LiveData<List<Post>>

    suspend fun fetchPost()
}
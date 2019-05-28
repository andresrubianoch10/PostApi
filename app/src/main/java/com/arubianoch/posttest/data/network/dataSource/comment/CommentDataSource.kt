package com.arubianoch.posttest.data.network.dataSource.comment

import androidx.lifecycle.LiveData
import com.arubianoch.posttest.data.network.response.Comment

/**
 * @author Andres Rubiano Del Chiaro
 */
interface CommentDataSource {
    val downloadComment: LiveData<List<Comment>>

    suspend fun fetchComment(postId: String)
}
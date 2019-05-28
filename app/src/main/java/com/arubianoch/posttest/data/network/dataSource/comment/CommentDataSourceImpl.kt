package com.arubianoch.posttest.data.network.dataSource.comment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arubianoch.posttest.data.network.ApiPostService
import com.arubianoch.posttest.data.network.response.Comment
import com.arubianoch.posttest.internal.NoConnectivityException

/**
 * @author Andres Rubiano Del Chiaro
 */
class CommentDataSourceImpl(private val postApiService: ApiPostService) : CommentDataSource {

    private val _downloadComment = MutableLiveData<List<Comment>>()
    override val downloadComment: LiveData<List<Comment>>
        get() = _downloadComment

    override suspend fun fetchComment(postId: String) {
        try {
            val fetchedComments = postApiService.getCommentsByPostId(postId).await()
            _downloadComment.postValue(fetchedComments)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }
}
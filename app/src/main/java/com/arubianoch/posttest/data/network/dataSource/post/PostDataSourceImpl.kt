package com.arubianoch.posttest.data.network.dataSource.post

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arubianoch.posttest.data.network.ApiPostService
import com.arubianoch.posttest.data.network.response.Post
import com.arubianoch.posttest.internal.NoConnectivityException

/**
 * @author Andres Rubiano Del Chiaro
 */
class PostDataSourceImpl(private val postApiService: ApiPostService) :
    PostDataSource {

    private val _downloadedPost = MutableLiveData<List<Post>>()
    override val downloadedPost: LiveData<List<Post>>
        get() = _downloadedPost

    override suspend fun fetchPost() {
        try {
            val fetchedPost = postApiService.getAllPosts().await()
            _downloadedPost.postValue(fetchedPost)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }
}

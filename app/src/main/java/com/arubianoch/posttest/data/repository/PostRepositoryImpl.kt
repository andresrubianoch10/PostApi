package com.arubianoch.posttest.data.repository

import androidx.lifecycle.LiveData
import com.arubianoch.posttest.data.db.dao.PostDao
import com.arubianoch.posttest.data.network.response.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

/**
 * @author Andres Rubiano Del Chiaro
 */
class PostRepositoryImpl(
    private val postDao: PostDao,
    private val postDataSource: PostDataSource
) : PostRepository {

    private var lastFetchedTime: ZonedDateTime? = null

    init {
        postDataSource.apply {
            downloadedPost.observeForever { allPost ->
                persistFetchedPost(allPost)
            }
        }
    }

    override suspend fun getPosts(): LiveData<List<Post>> {
        return withContext(Dispatchers.IO) {
            val postFetched = postDao.getPost()

            if (postFetched == null || postFetched.value.isNullOrEmpty()) {
                initPostData()
            }
            return@withContext postFetched
        }
    }

    private suspend fun initPostData() {
        fetchPosts()
    }

    override suspend fun fetchPosts() {
        postDataSource.fetchPost()
    }

    private fun persistFetchedPost(allPost: List<Post>?) {
        GlobalScope.launch(Dispatchers.IO) {
            postDao.upsert(allPost!!)
        }
    }

    override suspend fun insertPost(postUpdated: Post) {
        GlobalScope.launch(Dispatchers.IO) {
            postDao.upsert(postUpdated)
        }
    }

    override suspend fun getFavoritePost(): LiveData<List<Post>> {
        return withContext(Dispatchers.IO) {
            return@withContext postDao.getFavoritePost()
        }
    }

    override suspend fun updatePostAsFavorite(postId: String, isFavorite: Boolean) {
        GlobalScope.launch(Dispatchers.IO) {
            val favorite = if (isFavorite) "1" else "0"
            postDao.updatePostAsFavorite(postId, favorite)
        }
    }

    override suspend fun deletePostById(postId: String) {
        GlobalScope.launch(Dispatchers.IO) {
            postDao.deletePostById(postId)
        }
    }
}
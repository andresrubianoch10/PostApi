package com.arubianoch.posttest.ui.post

import com.arubianoch.posttest.data.network.response.Post
import com.arubianoch.posttest.data.repository.PostRepository
import com.arubianoch.posttest.internal.extensions.lazyDeferred
import com.arubianoch.posttest.ui.base.BasePostViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/**
 * @author Andres Rubiano Del Chiaro
 */
class PostViewModel (
    private val postRepository: PostRepository
) : BasePostViewModel(postRepository) {

    fun updatePost(postUpdated: Post) = runBlocking {
        withContext(Dispatchers.IO) {
            postRepository.insertPost(postUpdated)
        }
    }

    fun setFavoritePost(postId: String) = runBlocking {
        withContext(Dispatchers.IO) {
            postRepository.updatePostAsFavorite(postId, true)
        }
    }

    fun setUnFavoritePost(postId: String) = runBlocking {
        withContext(Dispatchers.IO) {
            postRepository.updatePostAsFavorite(postId, false)
        }
    }

    fun deletePostById(postId: String) = runBlocking {
        withContext(Dispatchers.IO) {
            postRepository.deletePostById(postId)
        }
    }

    fun refetchPosts() = runBlocking {
        withContext(Dispatchers.IO) {
            postRepository.fetchPosts()
        }
    }

    val posts by lazyDeferred {
        postRepository.getPosts()
    }

    val favoritePost by lazyDeferred {
        postRepository.getFavoritePost()
    }

    fun deleteAllInfo() = runBlocking {
        postRepository.deleteAllInfo()
    }
}

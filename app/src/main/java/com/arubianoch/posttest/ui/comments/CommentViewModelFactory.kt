package com.arubianoch.posttest.ui.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arubianoch.posttest.data.repository.PostRepository

/**
 * @author Andres Rubiano Del Chiaro
 */
class CommentViewModelFactory(
    private val postRepository: PostRepository,
    private val postId: String
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CommentViewModel(postRepository, postId) as T
    }
}
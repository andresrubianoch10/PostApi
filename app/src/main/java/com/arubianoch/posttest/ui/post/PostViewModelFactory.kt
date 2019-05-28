package com.arubianoch.posttest.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arubianoch.posttest.data.repository.PostRepository

/**
 * @author Andres Rubiano Del Chiaro
 */
class PostViewModelFactory(
    private val postRepository: PostRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostViewModel(postRepository) as T
    }
}
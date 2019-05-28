package com.arubianoch.posttest.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arubianoch.posttest.data.repository.PostRepository

/**
 * @author Andres Rubiano Del Chiaro
 */
class UserViewModelFactory(
    private val postRepository: PostRepository,
    private val postId: String
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(postRepository, postId) as T
    }
}
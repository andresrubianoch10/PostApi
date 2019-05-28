package com.arubianoch.posttest.ui.user

import com.arubianoch.posttest.data.repository.PostRepository
import com.arubianoch.posttest.internal.extensions.lazyDeferred
import com.arubianoch.posttest.ui.base.BasePostViewModel

/**
 * @author Andres Rubiano Del Chiaro
 */
class UserViewModel (
    private val postRepository: PostRepository,
    private val postId: String
) : BasePostViewModel(postRepository) {

    val user by lazyDeferred {
        postRepository.getUser(postId)
    }
}

package com.arubianoch.posttest.ui.comments

import com.arubianoch.posttest.data.repository.PostRepository
import com.arubianoch.posttest.internal.extensions.lazyDeferred
import com.arubianoch.posttest.ui.base.BasePostViewModel

/**
 * @author Andres Rubiano Del Chiaro
 */
class CommentViewModel (
    private val postRepository: PostRepository,
    private val postId: String
) : BasePostViewModel(postRepository) {

    val comments by lazyDeferred {
        postRepository.getComments(postId)
    }
}

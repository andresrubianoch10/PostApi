package com.arubianoch.posttest.ui.base

import androidx.lifecycle.ViewModel
import com.arubianoch.posttest.data.repository.PostRepository

/**
 * @author Andres Rubiano Del Chiaro
 */
abstract class BasePostViewModel(
    private val postRepository: PostRepository
) : ViewModel()
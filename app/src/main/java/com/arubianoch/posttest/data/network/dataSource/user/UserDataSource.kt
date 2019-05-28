package com.arubianoch.posttest.data.network.dataSource.user

import androidx.lifecycle.LiveData
import com.arubianoch.posttest.data.network.response.User

/**
 * @author Andres Rubiano Del Chiaro
 */
interface UserDataSource {
    val downloadedUser: LiveData<List<User>>

    suspend fun fetchUser()
}
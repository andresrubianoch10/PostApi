package com.arubianoch.posttest.data.network.dataSource.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arubianoch.posttest.data.network.ApiPostService
import com.arubianoch.posttest.data.network.response.User
import com.arubianoch.posttest.internal.exceptions.NoConnectivityException

/**
 * @author Andres Rubiano Del Chiaro
 */
class UserDataSourceImpl(private val postApiService: ApiPostService) :
    UserDataSource {

    private val _downloadedUser = MutableLiveData<List<User>>()
    override val downloadedUser: LiveData<List<User>>
        get() = _downloadedUser

    override suspend fun fetchUser() {
        try {
            val fetchedUser = postApiService.getUserInfo().await()
            _downloadedUser.postValue(fetchedUser)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }
}

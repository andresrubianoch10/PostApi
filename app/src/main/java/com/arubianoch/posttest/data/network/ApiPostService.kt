package com.arubianoch.posttest.data.network

import com.arubianoch.posttest.data.network.connectivity.ConnectivityInterceptor
import com.arubianoch.posttest.data.network.response.Comment
import com.arubianoch.posttest.data.network.response.Post
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Andres Rubiano Del Chiaro
 */
interface ApiPostService {

    @GET("/posts")
    fun getAllPosts(): Deferred<List<Post>>

    @GET("/comments")
    fun getCommentsByPostId(
        @Query("postId") postId: String
    ): Deferred<List<Comment>>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): ApiPostService {
            val requestInterceptor = Interceptor { chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", "KEY123")
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiPostService::class.java)
        }
    }
}
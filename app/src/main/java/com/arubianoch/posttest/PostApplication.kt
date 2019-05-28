package com.arubianoch.posttest

import android.app.Application
import com.arubianoch.posttest.data.db.PostDatabase
import com.arubianoch.posttest.data.network.ApiPostService
import com.arubianoch.posttest.data.network.connectivity.ConnectivityInterceptor
import com.arubianoch.posttest.data.network.connectivity.ConnectivityInterceptorImpl
import com.arubianoch.posttest.data.network.dataSource.comment.CommentDataSource
import com.arubianoch.posttest.data.network.dataSource.comment.CommentDataSourceImpl
import com.arubianoch.posttest.data.network.dataSource.post.PostDataSource
import com.arubianoch.posttest.data.network.dataSource.post.PostDataSourceImpl
import com.arubianoch.posttest.data.network.dataSource.user.UserDataSource
import com.arubianoch.posttest.data.network.dataSource.user.UserDataSourceImpl
import com.arubianoch.posttest.data.repository.PostRepository
import com.arubianoch.posttest.data.repository.PostRepositoryImpl
import com.arubianoch.posttest.ui.comments.CommentViewModelFactory
import com.arubianoch.posttest.ui.post.PostViewModelFactory
import com.arubianoch.posttest.ui.user.UserViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*

/**
 * @author Andres Rubiano Del Chiaro
 */
class PostsApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@PostsApplication))

        bind() from singleton { PostDatabase(instance()) }
        bind() from singleton { instance<PostDatabase>().commentDao() }
        bind() from singleton { instance<PostDatabase>().postDao() }
        bind() from singleton { instance<PostDatabase>().userDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApiPostService(instance()) }
        bind<PostDataSource>() with singleton {
            PostDataSourceImpl(
                instance()
            )
        }
        bind<CommentDataSource>() with singleton {
            CommentDataSourceImpl(
                instance()
            )
        }
        bind<UserDataSource>() with singleton {
            UserDataSourceImpl(
                instance()
            )
        }
        bind<PostRepository>() with singleton {
            PostRepositoryImpl(
                instance(),
                instance(),
                instance(),
                instance(),
                instance(),
                instance()
            )
        }
        bind() from provider { PostViewModelFactory(instance()) }
        bind() from factory { postId: String -> CommentViewModelFactory(instance(), postId) }
        bind() from factory { postId: String -> UserViewModelFactory(instance(), postId) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}
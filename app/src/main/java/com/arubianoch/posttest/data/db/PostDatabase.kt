package com.arubianoch.posttest.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arubianoch.posttest.data.db.dao.CommentDao
import com.arubianoch.posttest.data.db.dao.PostDao
import com.arubianoch.posttest.data.db.dao.UserDao
import com.arubianoch.posttest.data.network.response.Comment
import com.arubianoch.posttest.data.network.response.Post
import com.arubianoch.posttest.data.network.response.User

/**
 * @author Andres Rubiano Del Chiaro
 */

@Database(
    entities = [User::class, Comment::class, Post::class],
    version = 2
)
abstract class PostDatabase: RoomDatabase() {

    abstract fun postDao(): PostDao

    abstract fun commentDao(): CommentDao

    abstract fun userDao(): UserDao

    companion object {
        @Volatile private var instance: PostDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                PostDatabase::class.java, "posted_database")
                .build()
    }
}
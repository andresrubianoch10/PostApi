package com.arubianoch.posttest.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arubianoch.posttest.data.network.response.Comment

/**
 * @author Andres Rubiano Del Chiaro
 */
@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(comment: List<Comment>)

    @Query("SELECT * FROM comment WHERE postId = :postId")
    fun getComment(postId: String): LiveData<List<Comment>>

    @Query("DELETE FROM comment")
    fun deleteAllComments()
}
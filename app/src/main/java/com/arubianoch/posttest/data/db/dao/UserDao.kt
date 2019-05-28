package com.arubianoch.posttest.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * @author Andres Rubiano Del Chiaro
 */
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(user: List<User>)

    @Query("SELECT * FROM user WHERE id = :userId")
    fun getUserById(userId: String): LiveData<User>

    @Query("DELETE FROM user")
    fun deleteAllUsers()
}
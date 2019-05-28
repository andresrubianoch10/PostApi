package com.arubianoch.posttest.data.network.response

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Andres Rubiano Del Chiaro
 */
@Entity(tableName = "comment")
data class Comment(
    val body: String,
    val email: String,
    @PrimaryKey
    val id: Int,
    val name: String,
    val postId: Int
)
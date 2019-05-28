package com.arubianoch.posttest.data.network.response

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Andres Rubiano Del Chiaro
 */
@Entity(tableName = "post")
data class Post(
    val body: String,
    @PrimaryKey
    val id: Int,
    val title: String,
    val userId: Int
) {
    var isRead: Boolean = false
    var isFavorite: Boolean = false
}
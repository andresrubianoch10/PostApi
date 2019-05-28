package com.arubianoch.posttest.data.db.entity

import androidx.room.Embedded

/**
 * @author Andres Rubiano Del Chiaro
 */
data class  Address(
    val city: String,
    @Embedded(prefix = "user_address_geo")
    val geo: Geo,
    val street: String,
    val suite: String,
    val zipcode: String
)
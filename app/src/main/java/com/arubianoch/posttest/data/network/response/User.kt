package com.arubianoch.posttest.data.network.response

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.arubianoch.posttest.data.db.entity.Address
import com.arubianoch.posttest.data.db.entity.Company

/**
 * @author Andres Rubiano Del Chiaro
 */
@Entity(tableName = "user")
data class User(
    @Embedded(prefix = "address_")
    val address: Address,
    @Embedded(prefix = "company_")
    val company: Company,
    val email: String,
    @PrimaryKey
    val id: Int,
    val name: String,
    val phone: String,
    val username: String,
    val website: String
)
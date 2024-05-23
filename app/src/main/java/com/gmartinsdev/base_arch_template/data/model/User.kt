package com.gmartinsdev.base_arch_template.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  data class representing user local data
 */
@Entity
data class User(
    @PrimaryKey
    val id: Int,
    val name: String,
    val username: String,
    val email: String
)
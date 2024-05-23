package com.gmartinsdev.base_arch_template.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gmartinsdev.base_arch_template.data.model.User
import kotlinx.coroutines.flow.Flow

/**
 *  DAO representing all database operations related to user table
 */
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: User)

    @Query("SELECT * FROM user ORDER BY name ASC")
    fun getAll(): Flow<List<User>>

    @Query("SELECT * FROM user WHERE name LIKE '%' || :query || '%'")
    fun getUserByName(query: String): Flow<List<User>>
}
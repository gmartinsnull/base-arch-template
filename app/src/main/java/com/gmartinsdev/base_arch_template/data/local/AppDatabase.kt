package com.gmartinsdev.base_arch_template.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gmartinsdev.base_arch_template.data.model.User

/**
 *  class representing application database
 */
@Database([User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}
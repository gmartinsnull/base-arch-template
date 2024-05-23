package com.gmartinsdev.base_arch_template.di

import android.content.Context
import androidx.room.Room
import com.gmartinsdev.base_arch_template.data.UserRepository
import com.gmartinsdev.base_arch_template.data.local.AppDatabase
import com.gmartinsdev.base_arch_template.data.local.UserDao
import com.gmartinsdev.base_arch_template.data.remote.RemoteDataSource
import com.gmartinsdev.base_arch_template.data.remote.UserService
import com.gmartinsdev.base_arch_template.domain.GetUsers
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

/**
 *  hilt module responsible for injecting all local data related dependencies
 */
@InstallIn(SingletonComponent::class)
@Module
object DataModule {
    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext applicationContext: Context): AppDatabase =
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "database"
        ).build()

    @Singleton
    @Provides
    fun provideUserDao(database: AppDatabase): UserDao = database.getUserDao()

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Singleton
    @Provides
    fun provideRemoteDataSource(service: UserService): RemoteDataSource =
        RemoteDataSource(service)

    @Singleton
    @Provides
    fun provideMovieRepositoryImpl(
        remoteDataSource: RemoteDataSource,
        userDao: UserDao,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): UserRepository = UserRepository(remoteDataSource, userDao, ioDispatcher)

//    @Singleton
//    @Provides
//    fun provideGetMovieByTitle(
//        repository: UserRepository
//    ): GetMovieByTitle = GetMovieByTitle(repository)

    @Singleton
    @Provides
    fun provideGetMovies(
        repository: UserRepository
    ): GetUsers = GetUsers(repository)
}
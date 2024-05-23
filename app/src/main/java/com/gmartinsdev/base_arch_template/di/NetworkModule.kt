package com.gmartinsdev.base_arch_template.di

import com.gmartinsdev.base_arch_template.BuildConfig
import com.gmartinsdev.base_arch_template.data.remote.UserService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 *  hilt module responsible for injecting all network related dependencies
 */
@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun provideOkhttp(interceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Singleton
    @Provides
    fun provideConverterFactory(moshi: Moshi): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }

    @Singleton
    @Provides
    fun provideUserService(
        converterFactory: MoshiConverterFactory,
        okHttpClient: OkHttpClient
    ): UserService = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .addConverterFactory(converterFactory)
        .client(okHttpClient)
        .build()
        .create(UserService::class.java)
}
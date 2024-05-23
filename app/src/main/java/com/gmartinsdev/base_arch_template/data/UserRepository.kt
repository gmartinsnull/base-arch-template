package com.gmartinsdev.base_arch_template.data

import com.gmartinsdev.base_arch_template.data.model.User
import com.gmartinsdev.base_arch_template.data.local.UserDao
import com.gmartinsdev.base_arch_template.data.remote.ApiResult
import com.gmartinsdev.base_arch_template.data.remote.RemoteDataSource
import com.gmartinsdev.base_arch_template.data.remote.UserNotFoundThrowable
import com.gmartinsdev.base_arch_template.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import networkBoundResource
import javax.inject.Inject

/**
 *  repository responsible for handling user data from the network and storing it in the local database
 *  accordingly
 */
class UserRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val userDao: UserDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    /**
     * attempts to retrieve user data from local database. Otherwise, fetches from API
     */
    fun getUsers(): Flow<ApiResult<List<User>>> {
        return networkBoundResource(
            query = {
                userDao.getAll()
            },
            fetch = {
                remoteDataSource.fetchUsers()
            },
            saveFetchResult = { response ->
                val result = response.first()
                if (result.data.isNullOrEmpty() && result.error != null) { // api may return success but still contain error in body
                    throw UserNotFoundThrowable(result.error.message)
                } else {
                    result.data?.forEach {
                        userDao.insertAll(it)
                    }
                }
            }
        ).flowOn(ioDispatcher)
    }

    /**
     * retrieves list of all users from the database
     */
    fun getUsersFromDatabase(): Flow<ApiResult<List<User>>> = flow {
        val data = userDao.getAll().first()
        emit(ApiResult.success(data))
    }
}
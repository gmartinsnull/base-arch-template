package com.gmartinsdev.base_arch_template.data.remote

import com.gmartinsdev.base_arch_template.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 *  data source class responsible for fetching data from API endpoint
 */
class RemoteDataSource @Inject constructor(
    private val service: UserService
) {
    /**
     * fetches data from user API endpoint
     */
    fun fetchUsers(): Flow<ApiResult<List<User>>> = flow {
        emit(handleApiCall { service.fetchUsers() })
    }
}
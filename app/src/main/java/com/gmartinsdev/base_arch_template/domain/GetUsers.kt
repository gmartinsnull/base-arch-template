package com.gmartinsdev.base_arch_template.domain

import com.gmartinsdev.base_arch_template.data.UserRepository
import com.gmartinsdev.base_arch_template.data.model.User
import com.gmartinsdev.base_arch_template.data.remote.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *  usecase class responsible for isolating logic to get all users from the database
 */
class GetUsers @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(): Flow<ApiResult<List<User>>> {
        return repository.getUsers()
    }
}
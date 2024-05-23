package com.gmartinsdev.base_arch_template.data.remote

import com.gmartinsdev.base_arch_template.data.model.User
import retrofit2.Response
import retrofit2.http.GET

/**
 *  service class responsible for handling API calls
 */
interface UserService {
    @GET("/users")
    suspend fun fetchUsers(): Response<List<User>>
}
package io.test.randomusers.data.remote

import io.test.randomusers.domain.UserSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUsersService {
    /**
     * Get users
     */
    @GET("api/1.0/?seed=lydia")
    suspend fun searchUsers(
        @Query("page") page: Int,
        @Query("results") itemsPerPage: Int
    ): UserSearchResponse
}
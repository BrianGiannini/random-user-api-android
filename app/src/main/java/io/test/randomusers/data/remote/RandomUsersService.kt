/*
 * Copyright 2023 Brian Giannini
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.test.randomusers.data.remote

import io.test.randomusers.domain.UserSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUsersService {
    /**
     * Get users
     */
    @GET("api/1.0/?seed=sangui")
    suspend fun searchUsers(
        @Query("page") page: Int,
        @Query("results") itemsPerPage: Int
    ): UserSearchResponse
}
package io.test.randomusers.data.remote

import io.test.randomusers.domain.UserSearchResponse

const val ITEM_PER_PAGE = 10

class UserRemoteDataSource(private val apiService: RandomUsersService) {

    suspend fun getUsersFromRemote(page: Int): UserSearchResponse = apiService.searchUsers(page, ITEM_PER_PAGE)
}
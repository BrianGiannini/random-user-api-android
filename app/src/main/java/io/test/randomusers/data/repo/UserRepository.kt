package io.test.randomusers.data.repo

import io.test.randomusers.data.local.UserLocalDataSource
import io.test.randomusers.data.remote.UserRemoteDataSource
import io.test.randomusers.domain.model.RandomUser

/**
 * Repository class that works with local and remote data sources.
 */
class UserRepository(
    private val remoteDataSource: UserRemoteDataSource,
    private val localDataSource: UserLocalDataSource
) {

    suspend fun getUsersFromRemote(page: Int) = remoteDataSource.getUsersFromRemote(page)

    fun getUsersFromLocal() = localDataSource.getUsersFromLocal()

    suspend fun insertAllUsers(users: List<RandomUser>) = localDataSource.insertAllUsers(users)
}

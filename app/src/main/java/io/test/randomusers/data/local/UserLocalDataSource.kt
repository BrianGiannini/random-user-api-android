package io.test.randomusers.data.local

import io.test.randomusers.data.db.AppDatabase
import io.test.randomusers.domain.model.RandomUser
import kotlinx.coroutines.flow.Flow

class UserLocalDataSource(private val database: AppDatabase) {

    fun getUsersFromLocal(): Flow<List<RandomUser>> = database.userDao().getUsers()

    suspend fun insertAllUsers(users: List<RandomUser>) = database.userDao().insertAll(users)
}
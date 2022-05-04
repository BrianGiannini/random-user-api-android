package io.test.randomusers.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.test.randomusers.domain.model.RandomUser
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<RandomUser>)

    @Query("SELECT * FROM users")
    fun getUsers(): Flow<List<RandomUser>>

    @Query("DELETE FROM users")
    suspend fun clearUsers()

}
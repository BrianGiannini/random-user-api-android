package io.test.randomusers.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.test.randomusers.data.local.UserDao
import io.test.randomusers.domain.Converters
import io.test.randomusers.domain.model.RandomUser

@Database(entities = [RandomUser::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}
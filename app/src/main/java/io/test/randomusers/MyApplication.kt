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

package io.test.randomusers

import android.app.Application
import androidx.room.Room
import io.test.randomusers.data.db.AppDatabase
import io.test.randomusers.data.local.UserDao
import io.test.randomusers.data.local.UserLocalDataSource
import io.test.randomusers.data.remote.RandomUsersService
import io.test.randomusers.data.remote.UserRemoteDataSource
import io.test.randomusers.data.repo.UserRepository
import io.test.randomusers.ui.user_list.GetUsersViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://randomuser.me/"

class MyApplication : Application() {

    private fun provideUsersApi(retrofit: Retrofit): RandomUsersService = retrofit.create(RandomUsersService::class.java)

    private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient().newBuilder().addInterceptor(logger).build()
    }

    private fun provideDataBase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "random_user.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    private fun provideDao(dataBase: AppDatabase): UserDao {
        return dataBase.userDao()
    }

    override fun onCreate() {
        super.onCreate()

        val appModule = module {
            single { provideDataBase(androidApplication())}
            single { provideDao(get())}
            single { UserRemoteDataSource(get()) }
            single { UserLocalDataSource(get()) }
            single { UserRepository(get(), get()) }

            viewModel { GetUsersViewModel(get()) }
        }

        val networkModule = module {
            factory { provideUsersApi(get()) }
            factory { provideOkHttpClient() }
            single { provideRetrofit(get()) }
        }


        startKoin {
            androidContext(this@MyApplication)
            androidLogger()
            modules(appModule, networkModule)
        }

    }
}
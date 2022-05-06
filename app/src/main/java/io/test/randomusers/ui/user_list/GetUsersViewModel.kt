package io.test.randomusers.ui.user_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.test.randomusers.data.repo.UserRepository
import io.test.randomusers.domain.model.RandomUser
import io.test.randomusers.domain.toRandomUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class GetUsersViewModel(private val repository: UserRepository) : ViewModel() {

    private val _state = MutableStateFlow(UsersListState(false))
    val state: StateFlow<UsersListState> = _state

    init {
        viewModelScope.launch {
            getUsersFromLocal(repository)
        }
    }

    private var cachedUserList = mutableListOf<RandomUser>()
    private var page = 1

    suspend fun getUsers() {
        try {
            _state.emit(UsersListState(true))
            val users = repository.getUsersFromRemote(page).results
            val listUsers = mutableListOf<RandomUser>()
            for (user in users) {
                listUsers.add(user.toRandomUser())
            }
            repository.insertAllUsers(listUsers)
            _state.emit(UsersListState(users = listUsers.toList()))
        } catch (e: HttpException) {
            _state.emit(UsersListState(users = cachedUserList))
        } catch (e: IOException) {
            _state.emit(UsersListState(users = cachedUserList))
        }

    }

    private suspend fun getUsersFromLocal(repository: UserRepository) {
        repository.getUsersFromLocal().collect {
            cachedUserList.addAll(it)
        }

    }

    suspend fun loadDataNextPage() {
        page += 1
        getUsers()
    }

}

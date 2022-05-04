package io.test.randomusers.ui.user_list

import io.test.randomusers.domain.model.RandomUser

data class UsersListState (
    val isLoading: Boolean = false,
    val users: List<RandomUser> = emptyList(),
    val error: String = ""
)
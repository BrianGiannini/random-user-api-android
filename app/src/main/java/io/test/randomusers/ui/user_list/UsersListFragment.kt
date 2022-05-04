package io.test.randomusers.ui.user_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.test.randomusers.R
import io.test.randomusers.databinding.UsersListFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext

class UsersListFragment : CoroutineScope, Fragment(R.layout.users_list_fragment) {

    private val binding by lazy { UsersListFragmentBinding.inflate(layoutInflater) }
    private val viewModel: GetUsersViewModel by viewModel()

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = job + Dispatchers.Main

    private lateinit var usersAdapter: UsersAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = binding.root

    override fun onResume() {
        super.onResume()

        setupRecyclerView()
        displayedUsers()
    }

    private fun displayedUsers() {

        launch {
            with(viewModel) {
                getUsers()
                state.collect {
                    if (it.isLoading) {
                        // TODO: can manage loading state in UI here
                    }
                    val users = it.users
                    if (usersAdapter.currentList.isNullOrEmpty()) {
                        usersAdapter.submitList(users.toMutableList())
                    } else {
                        usersAdapter.submitList(usersAdapter.currentList + users.toMutableList())
                    }
                }

            }
        }
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(activity)

        val scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int) {
                launch {
                    viewModel.loadDataNextPage()
                }
            }
        }

        with(binding.recyclerView) {
            usersAdapter = UsersAdapter()
            adapter = usersAdapter
            addOnScrollListener(scrollListener)
            layoutManager = linearLayoutManager
        }
    }

}
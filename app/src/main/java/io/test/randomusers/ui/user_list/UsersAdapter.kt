package io.test.randomusers.ui.user_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.test.randomusers.databinding.UserViewItemBinding
import io.test.randomusers.domain.model.RandomUser
import io.test.randomusers.ui.user_list.UsersAdapter.*


/**
 * Adapter for the list of [RandomUser]
 */
class UsersAdapter : ListAdapter<RandomUser, UserViewHolder>(DiffCallback()) {

    class UserViewHolder(private val viewBinding: UserViewItemBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        private var user: RandomUser? = null

        init {
            viewBinding.root.setOnClickListener {
                user?.let {
                    val navController = Navigation.findNavController(itemView)
                    val action = UsersListFragmentDirections.actionUserDetailsFragmentToUsersListFragment(it)
                    navController.navigate(action)
                }

            }
        }

        fun bind(userItem: RandomUser) {
            with(viewBinding) {
                user = userItem
                userName.text = userItem.name
                userEmail.text = userItem.email
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = UserViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<RandomUser>() {
        override fun areItemsTheSame(oldItem: RandomUser, newItem: RandomUser): Boolean {
            return (oldItem.id == newItem.id)
        }

        override fun areContentsTheSame(oldItem: RandomUser, newItem: RandomUser): Boolean {
            return oldItem == newItem
        }
    }
}
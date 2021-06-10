package io.explod.paging.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import io.explod.paging.data.Coro
import io.explod.paging.data.model.User
import io.explod.paging.databinding.ViewHolderUserBinding
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

class UserAdapter(
    coro: Coro,
    private val lifecycleOwner: LifecycleOwner,
) : PagingDataAdapter<User, UserAdapter.UserViewHolder>(
    diffCallback = diffCallback,
    mainDispatcher = coro.ui,
    workerDispatcher = coro.default,
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderUserBinding.inflate(inflater, parent, false).also {
            it.lifecycleOwner = lifecycleOwner
        }
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position) ?: return
        holder.bind(user)
    }

    inner class UserViewHolder(private val binding: ViewHolderUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.user = user
        }
    }

    @Singleton
    class Factory @Inject constructor(
        private val coroProvider: Provider<Coro>,
    ) {
        fun create(lifecycleOwner: LifecycleOwner): UserAdapter {
            return UserAdapter(coroProvider.get(), lifecycleOwner)
        }
    }

    companion object {
        private val diffCallback = object : ItemCallback<User>() {

            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }
}
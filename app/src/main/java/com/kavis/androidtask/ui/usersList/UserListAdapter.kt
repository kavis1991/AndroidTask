package com.kavis.androidtask.ui.usersList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.kavis.androidtask.data.models.User
import com.kavis.androidtask.databinding.ItemUserBinding

class UserListAdapter(private val listener: UserItemListener) :
    PagedListAdapter<User, UserViewHolder>(DIFF_CALLBACK){

    interface UserItemListener {
        fun onUserClicked(userId: String)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding: ItemUserBinding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding, listener)
    }


    override fun onBindViewHolder(holder: UserViewHolder, position: Int)  {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<User> = object: DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: User, newItem: User) =
                oldItem == newItem
        }
    }
}

class UserViewHolder(private val itemBinding: ItemUserBinding, private val listener: UserListAdapter.UserItemListener) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var user: User

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: User) {
        this.user = item
        itemBinding.tvName.text = "${item.title} ${item.firstName} ${item.lastName}"
        itemBinding.tvId.text = item.id
        Glide.with(itemBinding.root)
            .load(item.picture)
            .transform(CircleCrop())
            .into(itemBinding.ivProfile)
    }

    override fun onClick(v: View?) {
        listener.onUserClicked(user.id)
    }
}

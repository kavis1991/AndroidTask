package com.kavis.androidtask.ui.usersList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.kavis.androidtask.data.models.User
import com.kavis.androidtask.databinding.ItemUserBinding

class UserListAdapter(private val listener: UserItemListener) : RecyclerView.Adapter<UserViewHolder>() {

    interface UserItemListener {
        fun onUserClicked(characterId: String)
    }

    private val items = ArrayList<User>()

    fun setItems(items: ArrayList<User>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding: ItemUserBinding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) = holder.bind(items[position])
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

package com.high5ive.android.moira.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.User
import com.high5ive.android.moira.databinding.UserItemBinding

class UserAdapter(val items: List<User>,
                  private val clickListener: (index: Int) -> Unit) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>(){
    class UserViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item, parent, false)
        val viewHolder=
            UserViewHolder(
                UserItemBinding.bind(view)
            )

        view.setOnClickListener {
            clickListener.invoke(viewHolder.adapterPosition + 1)
        }
        return viewHolder
    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.binding.user = items[position]
    }
}
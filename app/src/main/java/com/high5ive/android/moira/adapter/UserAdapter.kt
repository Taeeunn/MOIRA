package com.high5ive.android.moira.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.User
import com.high5ive.android.moira.data.retrofit.UserPoolItem
import com.high5ive.android.moira.databinding.UserItemBinding
import kotlinx.android.synthetic.main.user_item.view.*

class UserAdapter(val items: List<UserPoolItem>,
                  private val clickListener: (index: Int, type: Int) -> Unit) :
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
            clickListener.invoke(viewHolder.binding.user!!.userPoolId, 0)
        }


        view.interest_btn.setOnClickListener {
            clickListener.invoke(viewHolder.binding.user!!.userPoolId, 1)
        }

        return viewHolder
    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.binding.user = items[position]

        if (items[position].likedByUser){
            Log.v("hhh", items[position].likedByUser.toString())
            holder.binding.interestBtn.setBackgroundResource(R.drawable.ic_full_heart)
        } else{
            Log.v("hhh", items[position].likedByUser.toString())
            holder.binding.interestBtn.setBackgroundResource(R.drawable.ic_empty_heart)
        }
    }
}
package com.high5ive.android.moira.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.Comment
import com.high5ive.android.moira.data.retrofit.ChatItem
import com.high5ive.android.moira.databinding.ChatItemBinding
import com.high5ive.android.moira.databinding.CommentItemBinding

/**
 * @author Taeeun Kim
 * @email xodms8713@gmail.com
 * @created 2021-03-29
 */
class ChatAdapter(val items: List<ChatItem>,
                  private val clickListener: (index: Int) -> Unit) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>(){
    class ChatViewHolder(val binding: ChatItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_item, parent, false)
        val viewHolder=
            ChatViewHolder(
                ChatItemBinding.bind(view)
            )

        view.setOnClickListener {
            clickListener.invoke(viewHolder.binding.chat!!.chatRoomId)
        }
        return viewHolder
    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.binding.chat = items[position]

        if (items[position].unReadCount>=9){
            holder.binding.messageCount.text = "9+"
        } else if (items[position].unReadCount==0){
            holder.binding.messageCount.visibility= View.INVISIBLE
        }

        Glide.with(holder.binding.root.context)
            .load(items[position].opponentProfileImage)
            .error(R.drawable.ic_baseline_person_24) // ex) error(R.drawable.error)
            .into(holder.binding.userImage)
    }
}
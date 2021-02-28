package com.high5ive.android.moira.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.Comment
import com.high5ive.android.moira.data.User
import com.high5ive.android.moira.databinding.CommentItemBinding
import com.high5ive.android.moira.databinding.UserItemBinding

class CommentAdapter(val items: List<Comment>,
                     private val clickListener: (comment: Comment) -> Unit) :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>(){
    class CommentViewHolder(val binding: CommentItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.comment_item, parent, false)
        val viewHolder=
            CommentViewHolder(
                CommentItemBinding.bind(view)
            )

        view.setOnClickListener {
            clickListener.invoke(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.binding.comments = items[position]
    }
}
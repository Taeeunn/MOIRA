package com.high5ive.android.moira.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.high5ive.android.moira.data.Post
import com.high5ive.android.moira.R
import com.high5ive.android.moira.databinding.PostItemBinding
import kotlinx.android.synthetic.main.post_item.view.*

class PostAdapter(val items: List<Post>,
                  private val clickListener: (post: Post, type: Int) -> Unit) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>(){
    class PostViewHolder(val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_item, parent, false)
        val viewHolder=
            PostViewHolder(
                PostItemBinding.bind(view)
            )

        view.edit_post_btn.setOnClickListener {
            clickListener.invoke(items[viewHolder.adapterPosition], 0)
        }

        view.applicant_list_btn.setOnClickListener {
            clickListener.invoke(items[viewHolder.adapterPosition], 1)
        }


        return viewHolder
    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.binding.post = items[position]
    }
}
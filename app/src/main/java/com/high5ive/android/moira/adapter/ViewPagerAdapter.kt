package com.high5ive.android.moira.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.Post
import com.high5ive.android.moira.databinding.PostItemBinding

class ViewPagerAdapter(val items: List<Post>,
                  private val clickListener: (post: Post) -> Unit) :
    RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>(){
    class ViewPagerViewHolder(val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_item, parent, false)
        val viewHolder=
            ViewPagerViewHolder(
                PostItemBinding.bind(view)
            )

        view.setOnClickListener {
            clickListener.invoke(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.binding.post = items[position]
    }
}
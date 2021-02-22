package com.high5ive.android.moira

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.high5ive.android.moira.databinding.ReviewItemBinding

class ReviewAdapter(val items: List<Review>,
                     private val clickListener: (review: Review) -> Unit) :
    RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>(){
    class ReviewViewHolder(val binding: ReviewItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.review_item, parent, false)
        val viewHolder = ReviewViewHolder(ReviewItemBinding.bind(view))

        view.setOnClickListener {
            clickListener.invoke(items[viewHolder.adapterPosition])

        }

        return viewHolder
    }

    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.binding.review = items[position]
    }

}
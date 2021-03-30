package com.high5ive.android.moira.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.Review
import com.high5ive.android.moira.data.retrofit.ApplyUserReviewAllItem
import com.high5ive.android.moira.databinding.ReviewItemBinding

class ReviewAdapter(val items: List<ApplyUserReviewAllItem>) :
    RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>(){
    class ReviewViewHolder(val binding: ReviewItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.review_item, parent, false)
        val viewHolder =
            ReviewViewHolder(
                ReviewItemBinding.bind(view)
            )


        return viewHolder
    }

    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.binding.review = items[position]

        Glide.with(holder.binding.root.context)
            .load(items[position].userProfileUrl)
            .override(20, 20)
            .error(R.drawable.ic_baseline_person_24) // ex) error(R.drawable.error)
            .into(holder.binding.userImage)
    }

}
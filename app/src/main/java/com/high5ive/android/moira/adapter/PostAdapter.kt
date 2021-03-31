package com.high5ive.android.moira.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.retrofit.WrittenPostItem
import com.high5ive.android.moira.databinding.PostItemBinding
import kotlinx.android.synthetic.main.post_item.view.*

class PostAdapter(val items: List<WrittenPostItem>,
                  private val clickListener: (post: WrittenPostItem) -> Unit) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>(){
    class PostViewHolder(val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_item, parent, false)
        val viewHolder=
            PostViewHolder(
                PostItemBinding.bind(view)
            )

        view.applicant_list_btn.setOnClickListener {
            clickListener.invoke(items[viewHolder.adapterPosition])
        }



        return viewHolder
    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.binding.writtenpost = items[position]

        if (items[position].applicantCount>=9){
            holder.binding.applicantCount.text = "9+"
        }

        Glide.with(holder.binding.root.context)
            .load(items[position].projectImageUrl)
            .override(20, 20)
            .error(R.drawable.ic_baseline_public_24) // ex) error(R.drawable.error)
            .into(holder.binding.recruitImage)
    }
}
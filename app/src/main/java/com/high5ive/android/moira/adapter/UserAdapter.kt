package com.high5ive.android.moira.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.User
import com.high5ive.android.moira.data.retrofit.UserPoolItem
import com.high5ive.android.moira.databinding.UserItemBinding
import kotlinx.android.synthetic.main.activity_recruit_post_detail.*
import kotlinx.android.synthetic.main.user_item.view.*

class UserAdapter(val items: List<UserPoolItem>,
                  private val clickListener: (user: UserPoolItem, type: Int) -> Unit) :
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


            clickListener.invoke(items[viewHolder.adapterPosition], 0)
        }

        view.interest_btn.setOnClickListener {
            clickListener.invoke(items[viewHolder.adapterPosition], 1)


            if (!viewHolder.binding.user!!.likedByUser) {
                viewHolder.binding.user!!.likedByUser = true
                viewHolder.binding.interestBtn.setBackgroundResource(R.drawable.ic_full_heart_black)
            } else{
                viewHolder.binding.user!!.likedByUser = false
                viewHolder.binding.interestBtn.setBackgroundResource(R.drawable.ic_empty_heart_black)
            }
        }

        return viewHolder
    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.binding.user = items[position]


        if(items[position].likedByUser){
            holder.binding.interestBtn.setBackgroundResource(R.drawable.ic_full_heart_black)
        } else{
            holder.binding.interestBtn.setBackgroundResource(R.drawable.ic_empty_heart_black)
        }


        var hashtagNameList: List<String>? = items[position].hashtagList

        if (hashtagNameList!=null) {
            for (index in hashtagNameList.indices) {
                val tagName = hashtagNameList[index]

                val chip = Chip(holder.binding.root.context)
                val drawable =
                    ChipDrawable.createFromAttributes(holder.binding.root.context, null, 0, R.style.MaterialChipsAction)
                chip.setChipDrawable(drawable)

                chip.text = tagName
                holder.binding.tagGroup.addView(chip)
            }

        }

        Glide.with(holder.binding.root.context)
            .load(items[position].profileImage)
            .override(20, 20)
            .error(R.drawable.ic_baseline_person_24) // ex) error(R.drawable.error)
            .into(holder.binding.userImage)


    }

}
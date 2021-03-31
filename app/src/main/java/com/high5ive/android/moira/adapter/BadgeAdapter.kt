package com.high5ive.android.moira.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.retrofit.ComplimentMarkWithCountDto
import com.high5ive.android.moira.databinding.BadgeItemBinding

/**
 * @author Taeeun Kim
 * @email xodms8713@gmail.com
 * @created 2021-03-08
 */


class BadgeAdapter(val items: List<ComplimentMarkWithCountDto>) :
    RecyclerView.Adapter<BadgeAdapter.BadgeViewHolder>(){
    class BadgeViewHolder(val binding: BadgeItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BadgeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.badge_item, parent, false)
        val viewHolder =
            BadgeViewHolder(
                BadgeItemBinding.bind(view)
            )

        return viewHolder
    }

    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: BadgeViewHolder, position: Int) {
        holder.binding.badge = items[position]
    }

}
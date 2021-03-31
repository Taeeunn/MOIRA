package com.high5ive.android.moira.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.retrofit.TeamMemberItem
import com.high5ive.android.moira.databinding.MemberItemBinding

/**
 * @author Taeeun Kim
 * @email xodms8713@gmail.com
 * @created 2021-03-30
 */
class MemberAdapter(val items: List<TeamMemberItem>,
                    private val clickListener: (member: TeamMemberItem) -> Unit) :
    RecyclerView.Adapter<MemberAdapter.MemberViewHolder>(){
    class MemberViewHolder(val binding: MemberItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.member_item, parent, false)
        val viewHolder=
            MemberViewHolder(
                MemberItemBinding.bind(view)
            )

        view.setOnClickListener {
            clickListener.invoke(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.binding.member = items[position]

        if (items[position].userProjectRole=="LEADER"){
            holder.binding.leaderTag.visibility = View.VISIBLE
        }

        Glide.with(holder.binding.root.context)
            .load(items[position].userProfileImage)
            .override(20, 20)
            .error(R.drawable.ic_baseline_person_24) // ex) error(R.drawable.error)
            .into(holder.binding.contestImage)
    }
}
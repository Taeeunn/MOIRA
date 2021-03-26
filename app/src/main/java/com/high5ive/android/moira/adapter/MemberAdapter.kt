package com.high5ive.android.moira.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginLeft
import androidx.recyclerview.widget.RecyclerView
import com.high5ive.android.moira.data.Member
import com.high5ive.android.moira.R
import com.high5ive.android.moira.databinding.MemberItemBinding

class MemberAdapter(val items: List<Member>,
                    private val clickListener: (index: Int) -> Unit) :
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
            clickListener.invoke(viewHolder.adapterPosition+1)
        }
        return viewHolder
    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.binding.member = items[position]

        if (items[position].project_role=="팀장"){
            holder.binding.leaderTag.visibility = View.VISIBLE
        }
    }
}
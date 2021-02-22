package com.high5ive.android.moira

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.high5ive.android.moira.databinding.MemberItemBinding

class MemberAdapter(val items: List<Member>,
                    private val clickListener: (member: Member) -> Unit) :
    RecyclerView.Adapter<MemberAdapter.MemberViewHolder>(){
    class MemberViewHolder(val binding: MemberItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.member_item, parent, false)
        val viewHolder=MemberViewHolder(MemberItemBinding.bind(view))

        view.setOnClickListener {
            clickListener.invoke(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: MemberAdapter.MemberViewHolder, position: Int) {
        holder.binding.member = items[position]
    }
}
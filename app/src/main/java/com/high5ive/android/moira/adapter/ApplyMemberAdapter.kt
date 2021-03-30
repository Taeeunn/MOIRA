package com.high5ive.android.moira.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.retrofit.MyProjectTeammateResponseDTO
import com.high5ive.android.moira.data.retrofit.ProjectApplyUserItem
import com.high5ive.android.moira.databinding.ApplyMemberItemBinding
import com.high5ive.android.moira.databinding.MemberItemBinding

/**
 * @author Taeeun Kim
 * @email xodms8713@gmail.com
 * @created 2021-03-29
 */
class ApplyMemberAdapter(val items: List<ProjectApplyUserItem>,
                    private val clickListener: (applyUser: ProjectApplyUserItem) -> Unit) :
    RecyclerView.Adapter<ApplyMemberAdapter.ApplyMemberViewHolder>() {
    class ApplyMemberViewHolder(val binding: ApplyMemberItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplyMemberViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.apply_member_item, parent, false)
        val viewHolder =
            ApplyMemberViewHolder(
                ApplyMemberItemBinding.bind(view)
            )

        view.setOnClickListener {
            clickListener.invoke(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: ApplyMemberViewHolder, position: Int) {
        holder.binding.member = items[position]

        Glide.with(holder.binding.root.context)
            .load(items[position].imageUrl)
            .override(20, 20)
            .error(R.drawable.ic_baseline_person_24) // ex) error(R.drawable.error)
            .into(holder.binding.memberImage)
    }
}

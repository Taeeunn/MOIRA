package com.high5ive.android.moira.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginLeft
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.high5ive.android.moira.data.Member
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.retrofit.MyProjectTeammateResponseDTO
import com.high5ive.android.moira.databinding.MemberItemBinding
import com.high5ive.android.moira.databinding.TeammateItemBinding

class ProjectTeammateAdapter(val items: List<MyProjectTeammateResponseDTO>,
                    private val clickListener: (index: Int) -> Unit) :
    RecyclerView.Adapter<ProjectTeammateAdapter.ProjectTeammateViewHolder>(){
    class ProjectTeammateViewHolder(val binding: TeammateItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectTeammateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.teammate_item, parent, false)
        val viewHolder=
            ProjectTeammateViewHolder(
                TeammateItemBinding.bind(view)
            )

        view.setOnClickListener {
            clickListener.invoke(viewHolder.binding.member!!.userId)
        }
        return viewHolder
    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: ProjectTeammateViewHolder, position: Int) {
        holder.binding.member = items[position]

        if (items[position].leader){
            holder.binding.leaderTag.visibility = View.VISIBLE
        }

        Glide.with(holder.binding.root.context)
            .load(items[position].imageUrl)
            .override(20, 20)
            .error(R.drawable.ic_baseline_person_24) // ex) error(R.drawable.error)
            .into(holder.binding.contestImage)
    }
}
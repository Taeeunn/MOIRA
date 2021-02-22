package com.high5ive.android.moira

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.high5ive.android.moira.databinding.RecruitItemBinding

class RecruitAdapter(val items: List<Recruit>,
                     private val clickListener: (recruit: Recruit) -> Unit) :
    RecyclerView.Adapter<RecruitAdapter.RecruitViewHolder>(){
    class RecruitViewHolder(val binding: RecruitItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecruitViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recruit_item, parent, false)
        val viewHolder = RecruitViewHolder(RecruitItemBinding.bind(view))

        view.setOnClickListener {
            clickListener.invoke(items[viewHolder.adapterPosition])

        }

        return viewHolder
    }

    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: RecruitViewHolder, position: Int) {
        holder.binding.recruit = items[position]
    }

}
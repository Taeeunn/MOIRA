package com.high5ive.android.moira.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.Award
import com.high5ive.android.moira.databinding.AwardItemBinding

class AwardAdapter(val items: List<Award>,
                     private val clickListener: (award: Award) -> Unit) :
    RecyclerView.Adapter<AwardAdapter.AwardViewHolder>(){
    class AwardViewHolder(val binding: AwardItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AwardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.award_item, parent, false)
        val viewHolder =
            AwardViewHolder(
                AwardItemBinding.bind(view)
            )

        view.setOnClickListener {
            clickListener.invoke(items[viewHolder.adapterPosition])

        }

        return viewHolder
    }

    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: AwardViewHolder, position: Int) {
//        holder.binding.award = items[position]
    }

}
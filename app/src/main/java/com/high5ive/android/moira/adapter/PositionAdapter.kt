package com.high5ive.android.moira.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.retrofit.PositionCategory
import com.high5ive.android.moira.databinding.PositionItemBinding

class PositionAdapter(val items: List<PositionCategory>,
                      private val clickListener: (position: PositionCategory) -> Unit) :
    RecyclerView.Adapter<PositionAdapter.PositionViewHolder>(){
    class PositionViewHolder(val binding: PositionItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PositionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.position_item, parent, false)
        val viewHolder=
            PositionViewHolder(
                PositionItemBinding.bind(view)
            )

        view.setOnClickListener {
            clickListener.invoke(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: PositionViewHolder, position: Int) {
        holder.binding.position = items[position]
    }
}
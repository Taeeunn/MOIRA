package com.high5ive.android.moira

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.high5ive.android.moira.databinding.ApplyItemBinding

class ApplyAdapter(val items: List<Apply>,
                    private val clickListener: (apply: Apply) -> Unit) :
    RecyclerView.Adapter<ApplyAdapter.ApplyViewHolder>(){
    class ApplyViewHolder(val binding: ApplyItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.apply_item, parent, false)
        val viewHolder=ApplyViewHolder(ApplyItemBinding.bind(view))

        view.setOnClickListener {
            clickListener.invoke(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: ApplyAdapter.ApplyViewHolder, position: Int) {
        holder.binding.apply = items[position]
    }
}
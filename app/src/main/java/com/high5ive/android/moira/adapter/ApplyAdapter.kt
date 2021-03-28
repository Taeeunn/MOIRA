package com.high5ive.android.moira.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.high5ive.android.moira.data.Apply
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.retrofit.ApplyPostItem
import com.high5ive.android.moira.databinding.ApplyItemBinding
import kotlinx.android.synthetic.main.apply_item.view.*

class ApplyAdapter(val items: List<ApplyPostItem>,
                   private val clickListener: (index: Int, type: Int) -> Unit) :
    RecyclerView.Adapter<ApplyAdapter.ApplyViewHolder>(){
    class ApplyViewHolder(val binding: ApplyItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.apply_item, parent, false)
        val viewHolder=
            ApplyViewHolder(
                ApplyItemBinding.bind(view)
            )

        view.apply_list_btn.setOnClickListener {
            clickListener.invoke(viewHolder.binding.apply!!.projectApplyId, 0)
        }

        view.apply_cancle_btn.setOnClickListener {
            clickListener.invoke(viewHolder.binding.apply!!.projectApplyId, 1)
        }

        return viewHolder
    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: ApplyViewHolder, position: Int) {
        holder.binding.apply = items[position]
    }
}
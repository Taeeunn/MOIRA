package com.high5ive.android.moira.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.Recruit
import com.high5ive.android.moira.data.retrofit.RecruitPostItem
import com.high5ive.android.moira.databinding.RecruitItemBinding

class RecruitAdapter(val items: List<RecruitPostItem>,
                     private val clickListener: (index: Int) -> Unit) :
    RecyclerView.Adapter<RecruitAdapter.RecruitViewHolder>(){
    class RecruitViewHolder(val binding: RecruitItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecruitViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recruit_item, parent, false)
        val viewHolder =
            RecruitViewHolder(
                RecruitItemBinding.bind(view)
            )

        view.setOnClickListener {

            clickListener.invoke(viewHolder.binding.recruit!!.id)

        }


        return viewHolder
    }

    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: RecruitViewHolder, position: Int) {
        holder.binding.recruit = items[position]


    }

}
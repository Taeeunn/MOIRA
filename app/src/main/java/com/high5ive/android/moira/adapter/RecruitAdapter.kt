package com.high5ive.android.moira.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
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

        Glide.with(holder.binding.root.context)
            .load(items[position].imageUrl)
            .override(20, 20)
            .error(R.drawable.ic_baseline_public_24) // ex) error(R.drawable.error)
            .into(holder.binding.recruitImage)


        var hashtagNameList: List<String>? = items[position].hashtagList

        if (hashtagNameList!=null) {
            for (index in hashtagNameList.indices) {
                val tagName = hashtagNameList[index]

                val chip = Chip(holder.binding.root.context)
                val drawable =
                    ChipDrawable.createFromAttributes(holder.binding.root.context, null, 0, R.style.MaterialChipsAction)
                chip.setChipDrawable(drawable)

                chip.text = tagName
                holder.binding.tagGroup.addView(chip)
            }

        }



    }

}
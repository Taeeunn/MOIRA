package com.high5ive.android.moira.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.retrofit.RecruitPostItem
import com.high5ive.android.moira.data.retrofit.ScrapRecruitPostItem
import com.high5ive.android.moira.databinding.RecruitItemBinding
import com.high5ive.android.moira.databinding.ScrapRecruitItemBinding

/**
 * @author Taeeun Kim
 * @email xodms8713@gmail.com
 * @created 2021-03-28
 */
class ScrapRecruitAdapter(val items: List<ScrapRecruitPostItem>,
                          private val clickListener: (index: Int) -> Unit) :
    RecyclerView.Adapter<ScrapRecruitAdapter.ScrapRecruitViewHolder>(){
    class ScrapRecruitViewHolder(val binding: ScrapRecruitItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrapRecruitViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.scrap_recruit_item, parent, false)
        val viewHolder =
            ScrapRecruitViewHolder(
                ScrapRecruitItemBinding.bind(view)
            )

        view.setOnClickListener {

            clickListener.invoke(viewHolder.binding.scraprecruit!!.projectId)

        }


        return viewHolder
    }

    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: ScrapRecruitViewHolder, position: Int) {
        holder.binding.scraprecruit = items[position]

        Glide.with(holder.binding.root.context)
            .load(items[position].projectImageUrl)
            .override(20, 20)
            .error(R.drawable.ic_baseline_public_24) // ex) error(R.drawable.error)
            .into(holder.binding.recruitImage)

    }

}
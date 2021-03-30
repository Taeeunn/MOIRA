package com.high5ive.android.moira.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.Award
import com.high5ive.android.moira.data.retrofit.AlarmItem
import com.high5ive.android.moira.databinding.AlarmItemBinding
import com.high5ive.android.moira.databinding.AwardItemBinding

/**
 * @author Taeeun Kim
 * @email xodms8713@gmail.com
 * @created 2021-03-29
 */
class AlarmAdapter(val items: List<AlarmItem>,
                   private val clickListener: (index: Int, type: String) -> Unit) :
    RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>(){
    class AlarmViewHolder(val binding: AlarmItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.alarm_item, parent, false)
        val viewHolder =
            AlarmViewHolder(
                AlarmItemBinding.bind(view)
            )

        view.setOnClickListener {
            clickListener.invoke(viewHolder.binding.alarm!!.alarmTargetId, "")

        }

        return viewHolder
    }

    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        holder.binding.alarm = items[position]


    }

}
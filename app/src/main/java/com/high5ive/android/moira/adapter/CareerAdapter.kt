package com.high5ive.android.moira.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.retrofit.UserCareerResponseDto
import com.high5ive.android.moira.databinding.CareerItemBinding

/**
 * @author Taeeun Kim
 * @email xodms8713@gmail.com
 * @created 2021-03-31
 */
class CareerAdapter(val items: List<UserCareerResponseDto>) :
    RecyclerView.Adapter<CareerAdapter.CareerViewHolder>(){
    class CareerViewHolder(val binding: CareerItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CareerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.career_item, parent, false)
        val viewHolder =
            CareerViewHolder(
                CareerItemBinding.bind(view)
            )

        return viewHolder
    }

    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: CareerViewHolder, position: Int) {
        holder.binding.career = items[position]
    }

}
package com.high5ive.android.moira.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.retrofit.UserLinkResponseDto
import com.high5ive.android.moira.databinding.LinkItemBinding

/**
 * @author Taeeun Kim
 * @email xodms8713@gmail.com
 * @created 2021-03-31
 */
class LinkAdapter(val items: List<UserLinkResponseDto>) :
    RecyclerView.Adapter<LinkAdapter.LinkViewHolder>(){
    class LinkViewHolder(val binding: LinkItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.link_item, parent, false)
        val viewHolder =
            LinkViewHolder(
                LinkItemBinding.bind(view)
            )


        return viewHolder
    }

    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: LinkViewHolder, position: Int) {
        holder.binding.link = items[position]
    }

}
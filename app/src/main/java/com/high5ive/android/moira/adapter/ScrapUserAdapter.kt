package com.high5ive.android.moira.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.retrofit.ScrapUserPoolItem
import com.high5ive.android.moira.data.retrofit.UserPoolItem
import com.high5ive.android.moira.databinding.ScrapUserItemBinding
import com.high5ive.android.moira.databinding.UserItemBinding

/**
 * @author Taeeun Kim
 * @email xodms8713@gmail.com
 * @created 2021-03-28
 */
class ScrapUserAdapter(val items: List<ScrapUserPoolItem>,
                       private val clickListener: (index: Int) -> Unit) :
    RecyclerView.Adapter<ScrapUserAdapter.ScrapUserViewHolder>(){
    class ScrapUserViewHolder(val binding: ScrapUserItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrapUserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item, parent, false)
        val viewHolder=
            ScrapUserViewHolder(
                ScrapUserItemBinding.bind(view)
            )

        view.setOnClickListener {
            clickListener.invoke(viewHolder.binding.scrapuser!!.userPoolId)
        }
        return viewHolder
    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: ScrapUserViewHolder, position: Int) {
        holder.binding.scrapuser = items[position]
    }
}
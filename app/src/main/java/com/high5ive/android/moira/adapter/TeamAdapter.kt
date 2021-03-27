package com.high5ive.android.moira.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.Team

import com.high5ive.android.moira.databinding.TeamItemBinding
import kotlinx.android.synthetic.main.team_item.view.*


class TeamAdapter(val items: List<Team>,
                  private val clickListener: (type: Int, index: Int) -> Unit) :
    RecyclerView.Adapter<TeamAdapter.TeamViewHolder>(){
    class TeamViewHolder(val binding: TeamItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.team_item, parent, false)
        val viewHolder=
            TeamViewHolder(
                TeamItemBinding.bind(view)
            )

        view.setOnClickListener {
            clickListener.invoke(0, viewHolder.adapterPosition + 1)
        }

        view.evaluate_team_member_btn.setOnClickListener {
            clickListener.invoke(1, viewHolder.adapterPosition + 1)
        }


        return viewHolder
    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.binding.team = items[position]
    }
}
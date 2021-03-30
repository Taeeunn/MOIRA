package com.high5ive.android.moira.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.Team
import com.high5ive.android.moira.data.retrofit.MyTeamItem
import com.high5ive.android.moira.databinding.TeamItemBinding

import kotlinx.android.synthetic.main.team_item.view.*


class TeamAdapter(val items: List<MyTeamItem>,
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
            clickListener.invoke(0, viewHolder.binding.team!!.projectId)
        }

        view.evaluate_team_member_btn.setOnClickListener {
            clickListener.invoke(1, viewHolder.binding.team!!.projectId)
        }


        return viewHolder
    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.binding.team = items[position]
        
        if(!items[position].membersReviewed){
            holder.binding.evaluateTeamMemberBtn.visibility = View.VISIBLE
        }

        Glide.with(holder.binding.root.context)
            .load(items[position].imageUrl)
            .override(20, 20)
            .error(R.drawable.ic_baseline_public_24) // ex) error(R.drawable.error)
            .into(holder.binding.teamImage)

    }
}
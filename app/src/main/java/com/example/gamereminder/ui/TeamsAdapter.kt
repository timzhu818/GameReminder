package com.example.gamereminder.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gamereminder.R
import com.example.gamereminder.retrofit.Team

class TeamsAdapter(
    private val teams: List<Team?>,
    private val context: Context,
    private val listener: (Team) -> Unit
) :
    RecyclerView.Adapter<TeamsAdapter.TeamSearchHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamSearchHolder {
        return TeamSearchHolder(
            LayoutInflater.from(context).inflate(
                R.layout.team_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    override fun onBindViewHolder(holder: TeamSearchHolder, position: Int) {
        holder.bind(teams[position], context, listener)
    }

    class TeamSearchHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val teamName = itemView.findViewById<TextView>(R.id.name_team)
        private val sportName = itemView.findViewById<TextView>(R.id.name_sport)
        private val badgeImage = itemView.findViewById<AppCompatImageView>(R.id.badge_team)

        fun bind(team: Team?, context: Context, listener: (Team) -> Unit) {
            team?.let { curr ->
                teamName.text = curr.strTeam
                sportName.text = curr.strSport
                Glide.with(context).load(curr.strTeamBadge).centerCrop()
                    .placeholder(android.R.drawable.stat_sys_download).into(badgeImage)
                itemView.setOnClickListener { listener(curr) }

            }

        }
    }

}
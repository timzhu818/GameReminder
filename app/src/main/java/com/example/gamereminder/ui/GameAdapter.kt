package com.example.gamereminder.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gamereminder.R
import com.example.gamereminder.retrofit.Event

class GameAdapter(
    private val gameViewType: Int,
    private val games: List<Event?>,
    private val context: Context,
    private val listener:((Event) -> Unit)? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (gameViewType == PAST_GAME_TYPE) {
            return PastGameViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.past_game_item, parent, false
                )
            )
        } else {
            return FutureGameViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.future_game_item, parent, false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PastGameViewHolder -> holder.bind(games[position])
            is FutureGameViewHolder -> holder.bind(games[position], listener)
        }
    }

    class PastGameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val homeTeam = itemView.findViewById<TextView>(R.id.name_home_team)
        private val awayTeam = itemView.findViewById<TextView>(R.id.name_away_team)
        private val homeScore = itemView.findViewById<TextView>(R.id.home_score)
        private val awayScore = itemView.findViewById<TextView>(R.id.away_score)
        private val gameDate = itemView.findViewById<TextView>(R.id.game_date)
        private val gameLeague = itemView.findViewById<TextView>(R.id.game_league)
        fun bind(event: Event?) {
            homeTeam.text = event?.strHomeTeam
            awayTeam.text = event?.strAwayTeam
            homeScore.text = event?.intHomeScore
            awayScore.text = event?.intAwayScore
            gameDate.text = event?.dateEvent
            gameLeague.text = event?.strLeague
        }
    }

    class FutureGameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val homeTeam = itemView.findViewById<TextView>(R.id.name_home_team)
        private val awayTeam = itemView.findViewById<TextView>(R.id.name_away_team)
        private val gameDate = itemView.findViewById<TextView>(R.id.game_date)
        private val gameLeague = itemView.findViewById<TextView>(R.id.game_league)
        private val gameTime = itemView.findViewById<TextView>(R.id.game_time)
        fun bind(event: Event?, listener: ((Event) -> Unit)?) {
            homeTeam.text = event?.strHomeTeam
            awayTeam.text = event?.strAwayTeam
            gameDate.text = event?.dateEvent
            gameLeague.text = event?.strLeague
            gameTime.text = event?.strTime
        }
    }

    companion object {
        const val PAST_GAME_TYPE = 1
        const val FUTURE_GAME_TYPE = 2
    }
}
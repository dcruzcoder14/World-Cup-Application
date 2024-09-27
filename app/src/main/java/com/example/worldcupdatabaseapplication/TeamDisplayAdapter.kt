package com.example.worldcupdatabaseapplication

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.worldcupdatabaseapplication.databinding.TeamDisplayItemBinding
import java.util.*

class TeamDisplayAdapter : RecyclerView.Adapter<TeamDisplayAdapter.TeamDisplayViewHolder>() {

    private var teams: List<Team> = emptyList()

    // ViewHolder for a single team item in the RecyclerView
    class TeamDisplayViewHolder(private val binding: TeamDisplayItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(team: Team) {
            binding.flagImageView.setImageResource(getFlagResource(team.teamName))
            binding.teamNameTextView.text = team.teamName
            binding.continentTextView.text = team.continent
            binding.gamesPlayedTextView.text = team.played.toString()
            binding.gamesWonTextView.text = team.won.toString()
            binding.gamesDrawnTextView.text = team.drawn.toString()
            binding.gamesLostTextView.text = team.lost.toString()
            binding.totalPointsTextView.text = (team.won * 3 + team.drawn).toString()
        }

        private fun getFlagResource(teamName: String): Int {
            return when (teamName.lowercase(Locale.ROOT)) {
                "argentina" -> R.drawable.argentina
                "brazil" -> R.drawable.brazil
                "croatia" -> R.drawable.croatia
                "england" -> R.drawable.england
                "france" -> R.drawable.france
                "germany" -> R.drawable.germany
                "morocco" -> R.drawable.morocco
                "netherlands" -> R.drawable.netherlands
                "portugal" -> R.drawable.portugal
                "spain" -> R.drawable.spain
                // Add more cases for the other teams
                else -> R.drawable.argentina
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamDisplayViewHolder {
        // Inflate the team_display_item layout file and return a new TeamDisplayViewHolder object
        val binding = TeamDisplayItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return TeamDisplayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeamDisplayViewHolder, position: Int) {
        // Get the current team from the list of teams and bind the data to the ViewHolder
        val currentTeam = teams[position]
        holder.bind(currentTeam)
    }

    override fun getItemCount(): Int {
        // Returns the number of items in the RecyclerView.
        // In this case, the number of teams in the list.
        return teams.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setTeams(teams: List<Team>) {
        // Updates the list of teams and notifies the adapter that the data set has changed.
        // This function is called from outside the adapter to update the data in the RecyclerView.
        this.teams = teams
        notifyDataSetChanged()
    }
}


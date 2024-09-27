package com.example.worldcupdatabaseapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.worldcupdatabaseapplication.Team
import com.example.worldcupdatabaseapplication.TeamDiffCallback
import com.example.worldcupdatabaseapplication.databinding.DeleteTeamItemBinding

class TeamDeleteAdapter(
    private val context: Context,
    private val onTeamDeleted: (Team) -> Unit
) : ListAdapter<Team, TeamDeleteAdapter.ViewHolder>(TeamDiffCallback()) {

    // Inner ViewHolder class that holds a reference to the UI elements in each item view
    inner class ViewHolder(private val binding: DeleteTeamItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // Set up click listener for delete button
        init {
            binding.deleteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // Get the team at the clicked position
                    val team = getItem(position)
                    // Call the onTeamDeleted callback function with the clicked team
                    onTeamDeleted(team)
                }
            }
        }

        // Bind the data to the views
        fun bind(team: Team) {
            // Set the team name text view to the team's name
            binding.teamNameTextView.text = team.teamName
            // Get the resource name of the flag for the team
            val flagResourceName = getFlagResourceName(team.teamName)
            // Get the resource ID of the flag for the team
            val flagResourceId =
                context.resources.getIdentifier(flagResourceName, "drawable", context.packageName)
            // Set the flag image view to the flag for the team
            binding.flagImageView.setImageResource(flagResourceId)
        }
    }

    // Create a new ViewHolder when there are no existing ViewHolders available to recycle
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the item layout for the RecyclerView
        val inflater = LayoutInflater.from(parent.context)
        val binding = DeleteTeamItemBinding.inflate(inflater, parent, false)
        // Return a new ViewHolder with the inflated layout as its view
        return ViewHolder(binding)
    }

    // Bind data to the UI elements in a ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the team at the current position in the RecyclerView
        val team = getItem(position)
        // Bind the team data to the UI elements in the ViewHolder
        holder.bind(team)
    }

    // Map the team name to the corresponding flag resource name
    private fun getFlagResourceName(teamName: String): String {
        // Replace this mapping with the correct flag resource names for your project
        return when (teamName) {
            "Argentina" -> "argentina"
            "Brazil" -> "brazil"
            "Croatia" -> "croatia"
            "England" -> "england"
            "France" -> "france"
            "Germany" -> "germany"
            "Morocco" -> "morocco"
            "Netherlands" -> "netherlands"
            "Portugal" -> "portugal"
            "Spain" -> "spain"
            else -> "argentina"
        }
    }
}

package com.example.worldcupdatabaseapplication

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TeamListAdapter(private val onEditClicked: (teamId: Int) -> Unit) :
    RecyclerView.Adapter<TeamListAdapter.TeamViewHolder>() {

    private var teams: List<Team> = emptyList()

    //A mapping between the team names and their corresponding flag images
    private val flagMap = mapOf(
        "Argentina" to R.drawable.argentina,
        "Brazil" to R.drawable.brazil,
        "Croatia" to R.drawable.croatia,
        "England" to R.drawable.england,
        "France" to R.drawable.france,
        "Germany" to R.drawable.germany,
        "Morocco" to R.drawable.morocco,
        "Netherlands" to R.drawable.netherlands,
        "Portugal" to R.drawable.portugal,
        "Spain" to R.drawable.spain
    )


    // ViewHolder for a single team item in the RecyclerView
    class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Initialize views in the layout file
        val teamNameTextView: TextView = itemView.findViewById(R.id.teamNameTextView)
        val editButton: Button = itemView.findViewById(R.id.editButton)
        val flagImageView: ImageView = itemView.findViewById(R.id.flagImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        // Inflate the team_list_item layout file and return a new TeamViewHolder object
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.team_list_item, parent, false)
        return TeamViewHolder(itemView)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        //Get the current team from the list of teams
        val currentTeam = teams[position]

        //Set the team name in the text view
        holder.teamNameTextView.text = currentTeam.teamName

        //Get the corresponding flag image for the team
        val flagImage = flagMap[currentTeam.teamName]

        //If a flag image is found, set the image view's source to it
        if (flagImage != null) {
            holder.flagImageView.setImageResource(flagImage)
        }

        //Otherwise, set the image view's source to a default flag image
        else {
            holder.flagImageView.setImageResource(R.drawable.argentina)
        }


        holder.editButton.setOnClickListener {
            // Invoke the onEditClicked lambda with the current team's ID as the argument
            onEditClicked(currentTeam.teamID)
        }

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
        // The @SuppressLint("NotifyDataSetChanged") annotation suppresses a warning that
        // suggests using more specific change notifications instead of notifyDataSetChanged().
        this.teams = teams
        notifyDataSetChanged()
    }
}

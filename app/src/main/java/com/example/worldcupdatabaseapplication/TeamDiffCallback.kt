package com.example.worldcupdatabaseapplication

import androidx.recyclerview.widget.DiffUtil

// TeamDiffCallback extends the DiffUtil.ItemCallback class, which is used to
// calculate the difference between two lists in the RecyclerView adapter.
class TeamDiffCallback : DiffUtil.ItemCallback<Team>() {

    // This method checks whether two items (oldItem and newItem) have the same unique identifier.
    // In this case, it checks if the teamID of both items is the same.
    override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
        return oldItem.teamID == newItem.teamID
    }

    // This method checks whether two items (oldItem and newItem) have the same content.
    // In this case, it checks if the entire Team objects are equal.
    override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
        return oldItem == newItem
    }
}

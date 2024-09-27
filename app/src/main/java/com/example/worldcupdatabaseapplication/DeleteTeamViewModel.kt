package com.example.worldcupdatabaseapplication

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class DeleteTeamViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TeamRepository

    // Initialize repository using the DAO object
    init {
        val teamDao = AppDatabase.getInstance(application).teamDao()
        repository = TeamRepository(teamDao)
    }

    // Get the Flow object with teams that start with the search query string
    fun searchTeamsDynamic(query: String): Flow<List<Team>> {
        return repository.searchTeamsStartingWith(query.lowercase(Locale.ROOT))
    }

    // Delete the given team and display a toast message
    fun deleteTeam(team: Team) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteTeam(team)
            }
            Toast.makeText(getApplication(), "Team deleted successfully", Toast.LENGTH_SHORT).show()
        }
    }
}

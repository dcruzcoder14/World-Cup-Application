package com.example.worldcupdatabaseapplication

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class EditTeamViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TeamRepository

    // LiveData for search results
    val searchResults: LiveData<List<Team>> get() = _searchResults
    private val _searchResults = MutableLiveData<List<Team>>()

    // Initialize repository with TeamDao from the AppDatabase
    init {
        val teamDao = AppDatabase.getInstance(application).teamDao()
        repository = TeamRepository(teamDao)
    }

    // Check if the game stats are valid (played >= won + drawn + lost)
    fun areGameStatsValid(team: Team): Boolean {
        with(team) {
            return played >= (won + drawn + lost)
        }
    }

    // Filter search results
    fun searchTeams(query: String) {
        viewModelScope.launch {
            repository.searchTeamsDynamic(query).collect { teams ->
                val filteredTeams = teams.filter { team ->
                    team.teamName.lowercase(Locale.ROOT).startsWith(query.lowercase(Locale.ROOT))
                }
                _searchResults.value = filteredTeams
            }
        }
    }

    // Update team in the database
    fun updateTeam(updatedTeam: Team) {
        viewModelScope.launch {
            if (areGameStatsValid(updatedTeam)) {
                withContext(Dispatchers.IO) {
                    repository.updateTeam(updatedTeam)
                }
                //No need for a toast in the updateTeam function since we are already doing it in EditDetailsFragment
                //Toast.makeText(getApplication(), "Team updated successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(getApplication(), "Invalid game stats", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Get a team by ID
    fun getTeam(teamId: Int): LiveData<Team> {
        return repository.getTeamById(teamId)
    }

    // Get all teams
    fun getAllTeams(): LiveData<List<Team>> {
        return repository.getAllTeams()
    }

    // Check if a team with the given name and ID already exists in the database
    suspend fun checkTeamExists(teamName: String, teamId: Int): Boolean {
        return repository.checkTeamExists(teamName, teamId)
    }
}


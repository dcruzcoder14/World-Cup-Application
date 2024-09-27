package com.example.worldcupdatabaseapplication

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class TeamRepository(private val teamDao: TeamDao) {

    // This function returns all the teams stored in the database as LiveData objects
    fun getAllTeams(): LiveData<List<Team>> {
        val allTeams = teamDao.getAllTeams()
        Log.d("TeamRepository", "Number of teams in database: ${allTeams.value?.size}")
        return allTeams
    }

    // This function inserts a new team into the database
    suspend fun insertTeam(team: Team) {
        teamDao.addTeam(team)
    }

    // This function updates an existing team in the database
    suspend fun updateTeam(team: Team) {
        withContext(Dispatchers.IO) {
            teamDao.updateTeam(team)
        }
    }

    // This function deletes an existing team from the database
    suspend fun deleteTeam(team: Team) {
        teamDao.deleteTeam(team)
    }

    // This function searches the database for teams that match the given query
    fun searchTeams(query: String): Flow<List<Team>> {
        val searchQuery = "%$query%"
        return teamDao.searchTeams(searchQuery)
    }

    // This function returns a team with the given id as a LiveData object
    fun getTeamById(id: Int): LiveData<Team> {
        return teamDao.getTeamById(id)
    }

    // This function searches the database for teams that match the given query dynamically
    fun searchTeamsDynamic(query: String): Flow<List<Team>> {
        val searchQuery = "%$query%"
        return teamDao.searchTeams(searchQuery)
    }

    // This function checks if a team with the given name and id already exists in the database
    suspend fun checkTeamExists(teamName: String, teamId: Int): Boolean = withContext(Dispatchers.IO) {
        // Retrieve the team with the given name and id
        val existingTeam = teamDao.getTeamByName(teamName, teamId)

        // If an existing team with the same name and different id is found, return true
        return@withContext existingTeam != null && existingTeam.teamID != teamId
    }


    // Function to delete all data from the teams table
    fun deleteAllTeams() {
        val numDeleted = teamDao.deleteAllTeams()
        Log.d("TeamRepository", "Number of deleted teams: $numDeleted")
    }

    fun searchTeamsStartingWith(query: String): Flow<List<Team>> {
        val regex = "$query%"
        return teamDao.searchTeamsStartingWith(regex)
    }




    // New functions for sorting teams

    fun getTeamsSortedByName(): LiveData<List<Team>> {
        Log.d(TAG, "Getting teams sorted by name")
        return teamDao.getTeamsSortedByName()
    }

    fun getTeamsSortedByContinent(): LiveData<List<Team>> {
        return teamDao.getTeamsSortedByContinent()
    }

    fun getTeamsSortedByPoints(): LiveData<List<Team>> {
        return teamDao.getTeamsSortedByPoints()
    }


}


package com.example.worldcupdatabaseapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class AddTeamViewModel(application: Application) : AndroidViewModel(application) {

    private val teamDao: TeamDao
    private val teamRepository: TeamRepository

    init {
        val teamDatabase = AppDatabase.getInstance(application)
        teamDao = teamDatabase.teamDao()
        teamRepository = TeamRepository(teamDao)
    }

    //function that checks if the GameStats are valid
    private fun areGameStatsValid(team: Team): Boolean {
        val totalGames = team.played
        val totalWins = team.won
        val totalLosses = team.lost
        val totalDraws = team.drawn

        //returns the total games with a check to make sure the number of total games is
        //equal to win games + lost games + drawn games
        return totalGames >= (totalWins + totalLosses + totalDraws)
    }

    suspend fun insertTeam(team: Team): Boolean {
        return if (areGameStatsValid(team)) { // Check if the team's game stats are valid
            withContext(Dispatchers.IO) { // Run the following code on the IO thread
                teamDao.addTeam(team)
            }
            true // Return true to indicate that the team was successfully added to the database
        } else {
            false // Return false to indicate that the team's game stats are not valid and the team was not added to the database
        }
    }

    /**
     * Checks if a team with the given name already exists in the database.
     * @param teamName The name of the team to search for.
     * @return True if a team with the given name already exists in the database, false otherwise.
     */
    fun teamExists(teamName: String): Boolean {
        return runBlocking {
            withContext(Dispatchers.IO) {
                // Use the teamDao to search for the team with the given name.
                // If the returned list is not empty, then the team exists in the database.
                teamDao.searchTeamsList(teamName).isNotEmpty()
            }
        }
    }





}

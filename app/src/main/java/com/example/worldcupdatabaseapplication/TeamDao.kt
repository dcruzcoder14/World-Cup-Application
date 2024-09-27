package com.example.worldcupdatabaseapplication

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {
    // Inserts a list of teams into the database. If a team with the same ID already exists,
    // the existing team will be replaced.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(teams: List<Team>)

    // Inserts a new team into the database. If a team with the same name already exists,
    // the new team will be ignored.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addTeam(team: Team): Long

    // Updates an existing team in the database.
    @Update
    fun updateTeam(team: Team): Int

    // Deletes a team from the database.
    @Delete
    fun deleteTeam(team: Team): Int

    // Returns the number of teams in the database as a LiveData.
    @Query("SELECT COUNT(*) FROM teams")
    fun getNumberOfTeams(): LiveData<Int>

    // Returns all teams in the database as a LiveData.
    @Query("SELECT * FROM teams")
    fun getAllTeams(): LiveData<List<Team>>

    // Returns a single team with the specified ID as a LiveData.
    @Query("SELECT * FROM teams WHERE teamID = :id")
    fun getTeamById(id: Int): LiveData<Team>

    // Searches for teams whose names start with the specified query string and returns the results as a LiveData.
    @Query("SELECT * FROM teams WHERE teamName LIKE :query || '%'")
    fun searchTeamsLiveData(query: String): LiveData<List<Team>>

    // Searches for teams whose names match the specified query string exactly and returns the results as a List.
    @Query("SELECT * FROM teams WHERE teamName LIKE :query")
    fun searchTeamsList(query: String): List<Team>

    // Searches for teams whose names match the specified query string or start with it and returns the results as a Flow.
    @Query("SELECT * FROM teams WHERE teamName LIKE :query || '%'")
    fun searchTeams(query: String): Flow<List<Team>>

    // Returns the team with the specified name, excluding the team with the specified ID, or null if no such team exists.
    @Query("SELECT * FROM teams WHERE teamName = :name AND teamID != :id")
    fun getTeamByNameForEdit(name: String, id: Int): Team?

    // Returns the team with the specified name, excluding the team with the specified ID, or null if no such team exists.
    @Query("SELECT * FROM teams WHERE teamName = :name AND teamID != :id")
    fun getTeamByName(name: String, id: Int): Team?

    // Deletes all teams from the database.
    @Query("DELETE FROM teams")
    fun deleteAllTeams()


    // Returns a Flow of all teams whose name starts with the given query string.
    @Query("SELECT * FROM teams WHERE teamName LIKE :query")
    fun searchTeamsStartingWith(query: String): Flow<List<Team>>


    // Returns all teams in the database, sorted by name in ascending order, as a LiveData.
    @Query("SELECT DISTINCT teamID, teamName, continent, played, won, drawn, lost FROM teams ORDER BY teamName ASC")
    fun getTeamsSortedByName(): LiveData<List<Team>>


    // Returns all teams in the database, sorted by continent in ascending order and then by name
    // in ascending order, as a LiveData.
    @Query("SELECT DISTINCT teamID, teamName, continent, played, won, drawn, lost FROM teams ORDER BY continent ASC, teamName ASC")
    fun getTeamsSortedByContinent(): LiveData<List<Team>>


    // Returns all teams in the database, sorted by the number of points they have
    // (calculated as 3 * the number of wins plus the number of draws), then by the number
    // of losses, and then by name in ascending order, as a LiveData.
    @Query("SELECT DISTINCT teamID, teamName, continent, played, won, drawn, lost FROM teams " +
            "ORDER BY won * 3 + drawn DESC, lost ASC, teamName ASC")
    fun getTeamsSortedByPoints(): LiveData<List<Team>>





}


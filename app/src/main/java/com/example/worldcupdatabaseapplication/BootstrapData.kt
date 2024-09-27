package com.example.worldcupdatabaseapplication

object BootstrapData {
    private val teams = listOf(
        Team(teamID = 1, teamName = "Argentina", continent = "S. America", played = 7, won = 4, drawn = 1, lost = 2),
        Team(teamID = 2, teamName = "France", continent = "Europe", played = 7, won = 5, drawn = 1, lost = 1),
        Team(teamID = 3, teamName = "Croatia", continent = "Europe", played = 7, won = 2, drawn = 1, lost = 4),
        Team(teamID = 4, teamName = "Morocco", continent = "Africa", played = 7, won = 3, drawn = 2, lost = 3),
        Team(teamID = 5, teamName = "Netherlands", continent = "Europe", played = 6, won = 3, drawn = 0, lost = 2),
        Team(teamID = 6, teamName = "England", continent = "Europe", played = 5, won = 3, drawn = 1, lost = 1),
        Team(teamID = 7, teamName = "Brazil", continent = "S. America", played = 5, won = 3, drawn = 1, lost = 1),
        Team(teamID = 8, teamName = "Portugal", continent = "S. America", played = 6, won = 3, drawn = 2, lost = 0)
    )

    fun insertTeams(database: AppDatabase) {
        database.teamDao().insertAll(teams)
    }
}


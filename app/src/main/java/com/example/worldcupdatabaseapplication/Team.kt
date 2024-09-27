package com.example.worldcupdatabaseapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teams")
data class Team(
    @PrimaryKey(autoGenerate = true)
    val teamID: Int = 0,
    val teamName: String,
    val continent: String,
    val played: Int,
    val won: Int,
    val drawn: Int,
    val lost: Int
)

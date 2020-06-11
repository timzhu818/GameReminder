package com.example.gamereminder.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "myteam")
data class MyTeam(
    @PrimaryKey
    @ColumnInfo(name = "team_id")
    var teamId: String,
    @ColumnInfo(name = "team_name")
    var teamName: String,
    @ColumnInfo(name = "sports_name")
    var sportName: String,
    @ColumnInfo(name = "badge_image")
    var badgeImage: String
)

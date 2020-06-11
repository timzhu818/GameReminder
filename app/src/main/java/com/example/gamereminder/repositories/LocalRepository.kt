package com.example.gamereminder.repositories

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.example.gamereminder.retrofit.Team
import com.example.gamereminder.room.MyTeam
import com.example.gamereminder.room.MyTeamsDatabase
import com.example.gamereminder.utils.Constants.UNKNOWN

class LocalRepository(context: Context) {
    private val database: MyTeamsDatabase

    init {
        database = Room.databaseBuilder(context,
            MyTeamsDatabase::class.java, "room-db")
            .build()
    }

    suspend fun insert(team: Team) {
        val myTeam = MyTeam(teamId = team.idTeam ?: UNKNOWN,
            teamName = team.strTeam ?: UNKNOWN,
            sportName = team.strSport ?: UNKNOWN,
            badgeImage = team.strTeamBadge ?: UNKNOWN)
        database.myTeamsDao().insert(myTeam)
    }

    suspend fun getAllTeams(): List<MyTeam> {
        return database.myTeamsDao().getAllTeams()
    }

    suspend fun deleteTeam(teamId: String) {
        database.myTeamsDao().delete(teamId)
    }
}
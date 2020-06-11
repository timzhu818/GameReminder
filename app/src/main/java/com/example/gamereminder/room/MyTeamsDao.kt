package com.example.gamereminder.room

import androidx.room.*

@Dao
interface MyTeamsDao {

    @Query("Select * from myteam")
    fun getAllTeams(): List<MyTeam>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(myTeam: MyTeam)

    @Query("DELETE FROM myteam WHERE team_id = :teamId")
    fun delete(teamId: String)

}
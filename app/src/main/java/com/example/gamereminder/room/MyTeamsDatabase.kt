package com.example.gamereminder.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(MyTeam::class), version = 1)
abstract class MyTeamsDatabase: RoomDatabase() {
    abstract fun myTeamsDao(): MyTeamsDao
}

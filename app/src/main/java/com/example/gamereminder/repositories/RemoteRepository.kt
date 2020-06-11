package com.example.gamereminder.repositories

import com.example.gamereminder.retrofit.Event
import com.example.gamereminder.retrofit.RetrofitController
import com.example.gamereminder.retrofit.Team
import com.example.gamereminder.utils.Either
import com.example.gamereminder.utils.Failure
import com.example.gamereminder.utils.Success
import java.lang.Exception

class RemoteRepository {

    private var client = RetrofitController.requestApi

    suspend fun getTeams(teamQuery: String) : Either<String, List<Team?>?> =
        try {
            val response = client.getTeams(teamQuery)
            if (response.isSuccessful) {
                Success(response.body()?.teams)
            } else {
                Failure(response.errorBody().toString())
            }
        } catch (ex: Exception) {
            Failure(ex.message.toString())
        }

    suspend fun getFutureGames(teamId: String) : Either<String, List<Event?>?> =
        try {
            val response = client.getFutureGames(teamId)
            if (response.isSuccessful) {
                Success(response.body()?.results)
            } else {
                Failure(response.errorBody().toString())
            }
        } catch (ex: Exception) {
            Failure(ex.message.toString())
        }

    suspend fun getPastGames(teamId: String) : Either<String, List<Event?>?> =
        try {
            val response = client.getPastGames(teamId)
            if (response.isSuccessful) {
                Success(response.body()?.results)
            } else {
                Failure(response.errorBody().toString())
            }
        } catch (ex: Exception) {
            Failure(ex.message.toString())
        }
}
package com.example.gamereminder.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RequestApi {

    @GET("/api/v1/json/1/searchteams.php")
    suspend fun getTeams(@Query("t") team: String): Response<TeamsResponse>

    @GET("/api/v1/json/1/eventslast.php")
    suspend fun getPastGames(@Query ("id") id: String): Response<EventsResponse>

    @GET("/api/v1/json/1/eventsnext.php")
    suspend fun getFutureGames(@Query ("id") id: String): Response<EventsResponse>
}

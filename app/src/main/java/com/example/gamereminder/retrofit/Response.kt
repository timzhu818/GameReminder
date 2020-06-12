package com.example.gamereminder.retrofit

data class TeamsResponse(val teams: List<Team?>? = null)

data class ResultsResponse(val results: List<Event?>? = null)

data class EventsResponse(val events: List<Event?>? = null)

package com.example.gamereminder.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gamereminder.repositories.LocalRepository
import com.example.gamereminder.retrofit.Team
import com.example.gamereminder.room.MyTeam

class MainViewModel(localRepo: LocalRepository): BaseViewModel() {

    private val _showWelcome = MutableLiveData<Boolean>(false)
    private val _teamsList = MutableLiveData<List<Team>>()

    val showWelcome: LiveData<Boolean> = _showWelcome
    val teamList: LiveData<List<Team>> = _teamsList

    init {
        executeTask {
            localRepo.getAllTeams().let { teams ->
                if (teams.isNotEmpty()) transTeams(teams) else _showWelcome.postValue(true)
            }
        }
    }

    private fun transTeams(teams: List<MyTeam>){
        val listTeams = ArrayList<Team>()
        teams.forEach {myTeam ->
            Team(idTeam = myTeam.teamId, strTeam = myTeam.teamName, strTeamBadge = myTeam.badgeImage,
                strSport = myTeam.sportName).also { listTeams.add(it) }
        }
        _teamsList.postValue(listTeams)
    }
}
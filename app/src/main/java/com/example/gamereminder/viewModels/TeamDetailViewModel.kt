package com.example.gamereminder.viewModels

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gamereminder.repositories.LocalRepository
import com.example.gamereminder.retrofit.Team
import com.example.gamereminder.utils.Constants.TEAM_DATA

class TeamDetailViewModel(arguments: Bundle, private val localRepo: LocalRepository) :
    BaseViewModel() {

    private lateinit var teamData: Team

    private val _teamName = MutableLiveData<String>()
    private val _stadiumImage = MutableLiveData<String>()
    private val _badgeImage = MutableLiveData<String>()
    private val _stadiumName = MutableLiveData<String>()
    private val _leagueName = MutableLiveData<String>()
    private val _teamDesc = MutableLiveData<String>()

    val teamName: LiveData<String> = _teamName
    val stadiumImage: LiveData<String> = _stadiumImage
    val badgeImage: LiveData<String> = _badgeImage
    val stadiumName: LiveData<String> = _stadiumName
    val leagueName: LiveData<String> = _leagueName
    val teamDesc: LiveData<String> = _teamDesc

    init {
        arguments.getParcelable<Team>(TEAM_DATA)?.run {
            teamData = this
            _teamName.value = strTeam
            _stadiumImage.value = strStadiumThumb
            _badgeImage.value = strTeamBadge
            _stadiumName.value = strStadium
            _leagueName.value = strLeague
            _teamDesc.value = strDescriptionEN
        }
    }

    fun addTeam() {
        executeTask {
            localRepo.insert(teamData)
        }
    }

}
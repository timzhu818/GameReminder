package com.example.gamereminder.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gamereminder.repositories.RemoteRepository
import com.example.gamereminder.retrofit.Team
import com.example.gamereminder.utils.Failure
import com.example.gamereminder.utils.Success
import kotlinx.coroutines.*

class SearchTeamViewModel(private val remoteRepo: RemoteRepository) : BaseViewModel() {

    private val _teamsList = MutableLiveData<List<Team?>?>()
    val teamsList: LiveData<List<Team?>?> = _teamsList

    fun searchTeams(query: String?) {
        if (query == null) {
            _errorMessage.value = "Sorry, please enter keyword for your team search..."
            return
        }

        executeTask {
            when(val result = remoteRepo.getTeams(query)) {
                is Success -> _teamsList.postValue(result.r)
                is Failure -> _errorMessage.postValue(result.l)
            }
        }
    }

}
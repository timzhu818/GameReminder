package com.example.gamereminder.viewModels

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gamereminder.repositories.RemoteRepository
import com.example.gamereminder.retrofit.Event
import com.example.gamereminder.utils.Constants.TEAM_ID
import com.example.gamereminder.utils.Failure
import com.example.gamereminder.utils.Success

class PastGamesViewModel(bundle: Bundle, remoteRepo: RemoteRepository): BaseViewModel() {

    private val _pastGames = MutableLiveData<List<Event?>?>()

    val pastGames: LiveData<List<Event?>?> = _pastGames

    init {
        bundle.getString(TEAM_ID)?.let {
            executeTask {
                when (val result = remoteRepo.getPastGames(it)) {
                    is Success -> _pastGames.postValue(result.r)
                    is Failure -> _errorMessage.postValue(result.l)
                }
            }
        }
    }

}
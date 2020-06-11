package com.example.gamereminder.viewModels

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gamereminder.repositories.RemoteRepository
import com.example.gamereminder.retrofit.Event
import com.example.gamereminder.utils.Constants
import com.example.gamereminder.utils.Failure
import com.example.gamereminder.utils.Success

class FutureGamesViewModel(bundle: Bundle, remoteRepo: RemoteRepository): BaseViewModel() {

    private val _futureGames = MutableLiveData<List<Event?>?>()

    val futureGames: LiveData<List<Event?>?> = _futureGames

    init {
        bundle.getString(Constants.TEAM_ID)?.let {
            executeTask {
                when (val result = remoteRepo.getFutureGames(it)) {
                    is Success -> _futureGames.postValue(result.r)
                    is Failure -> _errorMessage.postValue(result.l)
                }
            }
        }
    }
}
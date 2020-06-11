package com.example.gamereminder.viewModels

import android.os.Bundle
import com.example.gamereminder.repositories.LocalRepository

class GamesViewModel(
    private val localRepo: LocalRepository
) : BaseViewModel() {

    fun deleteTeam(teamId: String?) {
        teamId?.let {
            executeTask {
                localRepo.deleteTeam(it)
            }
        }
    }


}
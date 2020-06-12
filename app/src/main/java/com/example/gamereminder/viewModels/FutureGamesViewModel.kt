package com.example.gamereminder.viewModels

import android.os.Bundle
import android.provider.CalendarContract.Instances.EVENT_ID
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.gamereminder.repositories.RemoteRepository
import com.example.gamereminder.retrofit.Event
import com.example.gamereminder.utils.Constants
import com.example.gamereminder.utils.Constants.GAME_INFO
import com.example.gamereminder.utils.Constants.REMINDER_OFF_SET
import com.example.gamereminder.utils.Constants.TIME_PATTERN
import com.example.gamereminder.utils.Failure
import com.example.gamereminder.utils.Success
import com.example.gamereminder.worker.NotificationWorker
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit

class FutureGamesViewModel(
    bundle: Bundle,
    remoteRepo: RemoteRepository,
    private val workManager: WorkManager
) : BaseViewModel() {

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

    fun buildNotification(event: Event) {
        val gameInfo =
            "The game between ${event.strHomeTeam} and ${event.strAwayTeam} is starting soon!"
        val eventId = event.idEvent?.toInt() ?: 1
        val inputData = Data.Builder().putString(GAME_INFO, gameInfo).putInt(EVENT_ID, eventId).build()
        calculateDuration(event)?.let {
            val notificationWorker = OneTimeWorkRequest.Builder(NotificationWorker::class.java)
                .setInitialDelay(it, TimeUnit.MILLISECONDS)
                .setInputData(inputData)
                .addTag(event.idEvent ?: gameInfo).build()
            workManager.enqueue(notificationWorker)
        }
    }

    private fun calculateDuration(event: Event): Long? {
        val dateTimeEvent = "${event.dateEvent}, ${event.strTime}"
        val dateTime = SimpleDateFormat(TIME_PATTERN, Locale.US).parse(dateTimeEvent)
        return dateTime?.let { gameTime -> gameTime.time - System.currentTimeMillis() - REMINDER_OFF_SET }
    }
}
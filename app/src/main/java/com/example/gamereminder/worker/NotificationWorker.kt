package com.example.gamereminder.worker

import android.app.NotificationManager
import android.content.Context
import android.provider.CalendarContract
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.gamereminder.utils.Constants.GAME_INFO


class NotificationWorker(private val context: Context, params: WorkerParameters) :
    Worker(context, params) {
    override fun doWork(): Result {

        val mBuilder = NotificationCompat.Builder(context)
            .setContentTitle("Game starting soon!")
            .setContentText(inputData.getString(GAME_INFO))

        getSystemService(context, NotificationManager::class.java)?.notify(
            inputData.getInt(
                CalendarContract.Instances.EVENT_ID,
                1
            ), mBuilder.build()
        )
        return Result.success()
    }
}
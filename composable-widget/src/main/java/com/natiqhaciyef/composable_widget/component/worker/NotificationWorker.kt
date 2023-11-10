package com.natiqhaciyef.composable_widget.component.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.natiqhaciyef.composable_widget.component.worker.util.NotificationSender

class NotificationWorker(val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    companion object {
        var title = ""
        var description = ""
    }

    override fun doWork(): Result {
        NotificationSender.sendNotification(
            context = context,
            title = title,
            description = description,
        )

        return Result.success()
    }
}
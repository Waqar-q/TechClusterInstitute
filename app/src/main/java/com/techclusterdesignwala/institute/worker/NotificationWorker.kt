package com.techclusterdesignwala.institute.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.techclusterdesignwala.institute.data.local.entity.NotificationEntity
import com.techclusterdesignwala.institute.data.repository.NotificationRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val notificationRepository: NotificationRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val type = inputData.getString("type") ?: return Result.success()
        val title = inputData.getString("title") ?: return Result.success()
        val message = inputData.getString("message") ?: return Result.success()

        notificationRepository.addNotification(
            NotificationEntity(
                title = title,
                message = message,
                type = type,
                timestamp = System.currentTimeMillis(),
                isRead = false
            )
        )
        return Result.success()
    }
}

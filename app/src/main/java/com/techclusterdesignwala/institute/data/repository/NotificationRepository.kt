package com.techclusterdesignwala.institute.data.repository

import com.techclusterdesignwala.institute.data.local.dao.NotificationDao
import com.techclusterdesignwala.institute.data.local.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepository @Inject constructor(
    private val notificationDao: NotificationDao
) {
    fun getAllNotifications(): Flow<List<NotificationEntity>> = notificationDao.getAll()

    fun getUnreadNotifications(): Flow<List<NotificationEntity>> = notificationDao.getUnread()

    fun getUnreadCount(): Flow<Int> = notificationDao.getUnreadCount()

    suspend fun addNotification(notification: NotificationEntity): Long =
        notificationDao.insert(notification)

    suspend fun markAsRead(id: Long) = notificationDao.markAsRead(id)

    suspend fun markAllAsRead() = notificationDao.markAllAsRead()

    suspend fun clearAll() = notificationDao.clearAll()
}

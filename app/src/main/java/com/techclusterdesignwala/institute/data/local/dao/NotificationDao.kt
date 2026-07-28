package com.techclusterdesignwala.institute.data.local.dao

import androidx.room.*
import com.techclusterdesignwala.institute.data.local.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
    @Query("SELECT * FROM notifications_history ORDER BY timestamp DESC")
    fun getAll(): Flow<List<NotificationEntity>>

    @Query("SELECT * FROM notifications_history WHERE isRead = 0 ORDER BY timestamp DESC")
    fun getUnread(): Flow<List<NotificationEntity>>

    @Query("SELECT COUNT(*) FROM notifications_history WHERE isRead = 0")
    fun getUnreadCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notification: NotificationEntity): Long

    @Query("UPDATE notifications_history SET isRead = 1 WHERE id = :id")
    suspend fun markAsRead(id: Long)

    @Query("UPDATE notifications_history SET isRead = 1")
    suspend fun markAllAsRead()

    @Query("DELETE FROM notifications_history")
    suspend fun clearAll()
}

package com.techclusterdesignwala.institute.data.local.dao

import androidx.room.*
import com.techclusterdesignwala.institute.data.local.entity.NoticeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoticeDao {
    @Query("SELECT * FROM notices ORDER BY date DESC")
    fun getAll(): Flow<List<NoticeEntity>>

    @Query("SELECT * FROM notices WHERE priority = :priority ORDER BY date DESC")
    fun getByPriority(priority: String): Flow<List<NoticeEntity>>

    @Query("SELECT * FROM notices WHERE id = :id")
    suspend fun getById(id: Long): NoticeEntity?

    @Query("SELECT * FROM notices WHERE date >= :afterDate ORDER BY date DESC")
    fun getRecent(afterDate: Long): Flow<List<NoticeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notice: NoticeEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(notices: List<NoticeEntity>)

    @Update
    suspend fun update(notice: NoticeEntity)

    @Delete
    suspend fun delete(notice: NoticeEntity)

    @Query("SELECT COUNT(*) FROM notices WHERE priority = 'HIGH' AND date >= :afterDate")
    fun getHighPriorityCount(afterDate: Long): Flow<Int>
}

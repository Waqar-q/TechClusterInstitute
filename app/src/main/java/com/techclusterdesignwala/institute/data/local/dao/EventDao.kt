package com.techclusterdesignwala.institute.data.local.dao

import androidx.room.*
import com.techclusterdesignwala.institute.data.local.entity.EventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Query("SELECT * FROM events ORDER BY date ASC")
    fun getAll(): Flow<List<EventEntity>>

    @Query("SELECT * FROM events WHERE date >= :fromDate AND date <= :toDate ORDER BY date ASC")
    fun getByMonth(fromDate: Long, toDate: Long): Flow<List<EventEntity>>

    @Query("SELECT * FROM events WHERE id = :id")
    suspend fun getById(id: Long): EventEntity?

    @Query("SELECT * FROM events WHERE eventType = :eventType ORDER BY date ASC")
    fun getByType(eventType: String): Flow<List<EventEntity>>

    @Query("SELECT * FROM events WHERE isHoliday = 1")
    fun getHolidays(): Flow<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: EventEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(events: List<EventEntity>)

    @Update
    suspend fun update(event: EventEntity)

    @Delete
    suspend fun delete(event: EventEntity)
}

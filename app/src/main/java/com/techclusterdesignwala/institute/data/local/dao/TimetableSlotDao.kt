package com.techclusterdesignwala.institute.data.local.dao

import androidx.room.*
import com.techclusterdesignwala.institute.data.local.entity.TimetableSlotEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimetableSlotDao {
    @Query("SELECT * FROM timetable_slots ORDER BY dayOfWeek ASC, startTime ASC")
    fun getAll(): Flow<List<TimetableSlotEntity>>

    @Query("SELECT * FROM timetable_slots WHERE dayOfWeek = :dayOfWeek ORDER BY startTime ASC")
    fun getByDay(dayOfWeek: Int): Flow<List<TimetableSlotEntity>>

    @Query("SELECT * FROM timetable_slots WHERE course = :course AND semester = :semester AND section = :section ORDER BY dayOfWeek ASC, startTime ASC")
    fun getByClass(course: String, semester: Int, section: String): Flow<List<TimetableSlotEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(slot: TimetableSlotEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(slots: List<TimetableSlotEntity>)

    @Update
    suspend fun update(slot: TimetableSlotEntity)

    @Delete
    suspend fun delete(slot: TimetableSlotEntity)
}

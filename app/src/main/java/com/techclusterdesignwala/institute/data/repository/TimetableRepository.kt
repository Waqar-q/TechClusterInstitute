package com.techclusterdesignwala.institute.data.repository

import com.techclusterdesignwala.institute.data.local.dao.TimetableSlotDao
import com.techclusterdesignwala.institute.data.local.entity.TimetableSlotEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimetableRepository @Inject constructor(
    private val timetableSlotDao: TimetableSlotDao
) {
    fun getAllSlots(): Flow<List<TimetableSlotEntity>> = timetableSlotDao.getAll()

    fun getSlotsByDay(dayOfWeek: Int): Flow<List<TimetableSlotEntity>> =
        timetableSlotDao.getByDay(dayOfWeek)

    fun getSlotsByClass(course: String, semester: Int, section: String): Flow<List<TimetableSlotEntity>> =
        timetableSlotDao.getByClass(course, semester, section)

    suspend fun addSlot(slot: TimetableSlotEntity): Long = timetableSlotDao.insert(slot)

    suspend fun addSlots(slots: List<TimetableSlotEntity>) = timetableSlotDao.insertAll(slots)

    suspend fun updateSlot(slot: TimetableSlotEntity) = timetableSlotDao.update(slot)

    suspend fun deleteSlot(slot: TimetableSlotEntity) = timetableSlotDao.delete(slot)
}

package com.techclusterdesignwala.institute.data.repository

import com.techclusterdesignwala.institute.data.local.dao.EventDao
import com.techclusterdesignwala.institute.data.local.entity.EventEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepository @Inject constructor(
    private val eventDao: EventDao
) {
    fun getAllEvents(): Flow<List<EventEntity>> = eventDao.getAll()

    fun getEventsByMonth(fromDate: Long, toDate: Long): Flow<List<EventEntity>> =
        eventDao.getByMonth(fromDate, toDate)

    suspend fun getEventById(id: Long): EventEntity? = eventDao.getById(id)

    suspend fun createEvent(event: EventEntity): Long = eventDao.insert(event)

    suspend fun updateEvent(event: EventEntity) = eventDao.update(event)

    suspend fun deleteEvent(event: EventEntity) = eventDao.delete(event)
}

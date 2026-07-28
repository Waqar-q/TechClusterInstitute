package com.techclusterdesignwala.institute.data.repository

import com.techclusterdesignwala.institute.data.local.dao.NoticeDao
import com.techclusterdesignwala.institute.data.local.entity.NoticeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoticeRepository @Inject constructor(
    private val noticeDao: NoticeDao
) {
    fun getAllNotices(): Flow<List<NoticeEntity>> = noticeDao.getAll()

    fun getNoticesByPriority(priority: String): Flow<List<NoticeEntity>> =
        noticeDao.getByPriority(priority)

    suspend fun getNoticeById(id: Long): NoticeEntity? = noticeDao.getById(id)

    suspend fun createNotice(notice: NoticeEntity): Long = noticeDao.insert(notice)

    suspend fun updateNotice(notice: NoticeEntity) = noticeDao.update(notice)

    suspend fun deleteNotice(notice: NoticeEntity) = noticeDao.delete(notice)
}

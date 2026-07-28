package com.techclusterdesignwala.institute.data.repository

import com.techclusterdesignwala.institute.data.local.dao.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import javax.inject.Singleton

data class DashboardData(
    val studentCount: Int,
    val presentToday: Int,
    val totalToday: Int,
    val pendingAssignments: Int,
    val upcomingEvents: Int,
    val highPriorityNotices: Int,
    val attendancePercentage: Float
)

@Singleton
class DashboardRepository @Inject constructor(
    private val studentDao: StudentDao,
    private val attendanceDao: AttendanceDao,
    private val assignmentDao: AssignmentDao,
    private val eventDao: EventDao,
    private val noticeDao: NoticeDao
) {
    fun getDashboardData(studentId: Long): Flow<DashboardData> {
        return combine(
            studentDao.count(),
            attendanceDao.getPresentCount(studentId),
            attendanceDao.getTotalCount(studentId),
            assignmentDao.getPendingCount(),
            eventDao.getAll(),
            noticeDao.getHighPriorityCount(0L)
        ) { studentCount, present, total, pending, events, notices ->
            DashboardData(
                studentCount = studentCount,
                presentToday = present,
                totalToday = total,
                pendingAssignments = pending,
                upcomingEvents = events.size,
                highPriorityNotices = notices,
                attendancePercentage = if (total > 0) (present.toFloat() / total * 100) else 0f
            )
        }
    }
}

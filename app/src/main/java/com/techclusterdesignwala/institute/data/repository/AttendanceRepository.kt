package com.techclusterdesignwala.institute.data.repository

import com.techclusterdesignwala.institute.data.local.dao.AttendanceDao
import com.techclusterdesignwala.institute.data.local.entity.AttendanceEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AttendanceRepository @Inject constructor(
    private val attendanceDao: AttendanceDao
) {
    fun getAttendanceByStudent(studentId: Long): Flow<List<AttendanceEntity>> =
        attendanceDao.getByStudentId(studentId)

    fun getAttendanceByStudentAndSubject(studentId: Long, subject: String): Flow<List<AttendanceEntity>> =
        attendanceDao.getByStudentAndSubject(studentId, subject)

    fun getAttendanceByDateRange(startDate: Long, endDate: Long): Flow<List<AttendanceEntity>> =
        attendanceDao.getByDateRange(startDate, endDate)

    fun getStudentAttendancePercentage(studentId: Long): Flow<Pair<Int, Int>> {
        return kotlinx.coroutines.flow.combine(
            attendanceDao.getPresentCount(studentId),
            attendanceDao.getTotalCount(studentId)
        ) { present, total -> Pair(present, total) }
    }

    suspend fun markAttendance(attendance: AttendanceEntity): Long =
        attendanceDao.insert(attendance)

    suspend fun markMultipleAttendance(attendanceList: List<AttendanceEntity>) =
        attendanceDao.insertAll(attendanceList)

    suspend fun updateAttendance(attendance: AttendanceEntity) =
        attendanceDao.update(attendance)

    suspend fun deleteAttendance(id: Long) =
        attendanceDao.delete(id)

    fun getDistinctSubjects(studentId: Long): Flow<List<String>> =
        attendanceDao.getDistinctSubjects(studentId)
}

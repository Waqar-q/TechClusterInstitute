package com.techclusterdesignwala.institute.data.repository

import com.techclusterdesignwala.institute.data.local.dao.*
import com.techclusterdesignwala.institute.data.local.entity.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TeacherRepository @Inject constructor(
    private val batchDao: BatchDao,
    private val enrollmentDao: EnrollmentDao,
    private val attendanceDao: AttendanceDao,
    private val assignmentDao: AssignmentDao,
    private val assignmentSubmissionDao: AssignmentSubmissionDao,
    private val resultDao: ResultDao,
    private val userDao: UserDao
) {
    fun getMyBatches(teacherId: Long): Flow<List<BatchEntity>> = batchDao.getByTeacher(teacherId)

    fun getEnrolledStudents(batchId: Long): Flow<List<EnrollmentEntity>> = enrollmentDao.getByBatch(batchId)

    suspend fun getUserName(userId: Long): String? = userDao.getById(userId)?.name

    fun getAttendanceByBatch(batchId: Long): Flow<List<AttendanceEntity>> = attendanceDao.getByBatch(batchId)

    suspend fun markAttendance(enrollmentId: Long, date: Long, status: String): Long {
        val existing = attendanceDao.getByEnrollmentAndDate(enrollmentId, date)
        return if (existing != null) {
            attendanceDao.update(existing.copy(status = status))
            existing.id
        } else {
            attendanceDao.insert(AttendanceEntity(enrollmentId = enrollmentId, date = date, status = status))
        }
    }

    fun getAssignments(batchId: Long): Flow<List<AssignmentEntity>> = assignmentDao.getByBatch(batchId)

    suspend fun createAssignment(batchId: Long, title: String, description: String, dueDate: Long): Long {
        return assignmentDao.insert(AssignmentEntity(batchId = batchId, title = title, description = description, dueDate = dueDate, assignedDate = System.currentTimeMillis()))
    }

    fun getSubmissions(assignmentId: Long): Flow<List<AssignmentSubmissionEntity>> = assignmentSubmissionDao.getByAssignment(assignmentId)

    suspend fun gradeSubmission(id: Long, marks: Float, feedback: String) {
        val sub = assignmentSubmissionDao.getByAssignmentAndStudent(id, 0) ?: return
    }

    suspend fun addResult(result: ResultEntity): Long = resultDao.insert(result)
}

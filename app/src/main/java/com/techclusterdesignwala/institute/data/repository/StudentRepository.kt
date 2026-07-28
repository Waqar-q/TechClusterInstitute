package com.techclusterdesignwala.institute.data.repository

import com.techclusterdesignwala.institute.data.local.dao.*
import com.techclusterdesignwala.institute.data.local.entity.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StudentRepository @Inject constructor(
    private val batchDao: BatchDao,
    private val enrollmentDao: EnrollmentDao,
    private val attendanceDao: AttendanceDao,
    private val assignmentDao: AssignmentDao,
    private val assignmentSubmissionDao: AssignmentSubmissionDao,
    private val resultDao: ResultDao,
    private val softwareDao: SoftwareDao
) {
    fun getMyBatches(studentId: Long): Flow<List<BatchEntity>> = batchDao.getByStudent(studentId)

    fun getMyEnrollments(studentId: Long): Flow<List<EnrollmentEntity>> = enrollmentDao.getByStudent(studentId)

    fun getAttendance(studentId: Long): Flow<List<AttendanceEntity>> = attendanceDao.getByStudent(studentId)

    fun getAssignments(studentId: Long): Flow<List<AssignmentEntity>> {
        return kotlinx.coroutines.flow.flow {
            batchDao.getByStudent(studentId).collect { batches ->
                val ids = batches.map { it.id }
                val allAssignments = mutableListOf<AssignmentEntity>()
                ids.forEach { id ->
                    assignmentDao.getByBatch(id).collect { allAssignments.addAll(it) }
                }
                emit(allAssignments.sortedBy { it.dueDate })
            }
        }
    }

    suspend fun submitAssignment(assignmentId: Long, studentId: Long): Long {
        return assignmentSubmissionDao.insert(
            AssignmentSubmissionEntity(
                assignmentId = assignmentId,
                studentId = studentId,
                submittedDate = System.currentTimeMillis(),
                status = "SUBMITTED"
            )
        )
    }

    fun getResults(studentId: Long): Flow<List<ResultEntity>> = resultDao.getByStudent(studentId)

    fun getPendingSubmissions(studentId: Long): Flow<Int> = assignmentSubmissionDao.getPendingCount(studentId)
}

package com.techclusterdesignwala.institute.data.local.dao

import androidx.room.*
import com.techclusterdesignwala.institute.data.local.entity.AssignmentSubmissionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AssignmentSubmissionDao {
    @Query("SELECT * FROM assignment_submissions WHERE assignmentId = :assignmentId")
    fun getByAssignment(assignmentId: Long): Flow<List<AssignmentSubmissionEntity>>

    @Query("SELECT * FROM assignment_submissions WHERE assignmentId = :assignmentId AND studentId = :studentId")
    suspend fun getByAssignmentAndStudent(assignmentId: Long, studentId: Long): AssignmentSubmissionEntity?

    @Query("SELECT COUNT(*) FROM assignment_submissions WHERE studentId = :studentId AND status = 'SUBMITTED'")
    fun getPendingCount(studentId: Long): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(submission: AssignmentSubmissionEntity): Long
}
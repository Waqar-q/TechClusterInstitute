package com.techclusterdesignwala.institute.data.local.dao

import androidx.room.*
import com.techclusterdesignwala.institute.data.local.entity.EnrollmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EnrollmentDao {
    @Query("SELECT * FROM enrollments WHERE batchId = :batchId")
    fun getByBatch(batchId: Long): Flow<List<EnrollmentEntity>>

    @Query("SELECT * FROM enrollments WHERE studentId = :studentId")
    fun getByStudent(studentId: Long): Flow<List<EnrollmentEntity>>

    @Query("UPDATE enrollments SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: Long, status: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(enrollment: EnrollmentEntity): Long
}
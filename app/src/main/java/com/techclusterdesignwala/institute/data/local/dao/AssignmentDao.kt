package com.techclusterdesignwala.institute.data.local.dao

import androidx.room.*
import com.techclusterdesignwala.institute.data.local.entity.AssignmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AssignmentDao {
    @Query("SELECT * FROM assignments ORDER BY dueDate ASC")
    fun getAll(): Flow<List<AssignmentEntity>>

    @Query("SELECT * FROM assignments WHERE batchId = :batchId ORDER BY dueDate ASC")
    fun getByBatch(batchId: Long): Flow<List<AssignmentEntity>>

    @Query("SELECT * FROM assignments WHERE status = :status ORDER BY dueDate ASC")
    fun getByStatus(status: String): Flow<List<AssignmentEntity>>

    @Query("SELECT * FROM assignments WHERE id = :id")
    suspend fun getById(id: Long): AssignmentEntity?

    @Query("SELECT * FROM assignments WHERE dueDate BETWEEN :start AND :end ORDER BY dueDate ASC")
    fun getByDateRange(start: Long, end: Long): Flow<List<AssignmentEntity>>

    @Query("SELECT COUNT(*) FROM assignments WHERE status = 'PENDING'")
    fun getPendingCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(assignment: AssignmentEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(assignments: List<AssignmentEntity>)

    @Update
    suspend fun update(assignment: AssignmentEntity)

    @Delete
    suspend fun delete(assignment: AssignmentEntity)
}
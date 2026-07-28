package com.techclusterdesignwala.institute.data.local.dao

import androidx.room.*
import com.techclusterdesignwala.institute.data.local.entity.BatchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BatchDao {
    @Query("SELECT * FROM batches ORDER BY name ASC")
    fun getAll(): Flow<List<BatchEntity>>

    @Query("SELECT * FROM batches WHERE id = :id")
    suspend fun getById(id: Long): BatchEntity?

    @Query("SELECT * FROM batches WHERE teacherId = :teacherId ORDER BY name ASC")
    fun getByTeacher(teacherId: Long): Flow<List<BatchEntity>>

    @Query("SELECT b.* FROM batches b INNER JOIN enrollments e ON b.id = e.batchId WHERE e.studentId = :studentId ORDER BY b.name ASC")
    fun getByStudent(studentId: Long): Flow<List<BatchEntity>>

    @Query("SELECT COUNT(*) FROM batches")
    fun count(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(batch: BatchEntity): Long

    @Update
    suspend fun update(batch: BatchEntity)

    @Delete
    suspend fun delete(batch: BatchEntity)
}
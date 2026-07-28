package com.techclusterdesignwala.institute.data.local.dao

import androidx.room.*
import com.techclusterdesignwala.institute.data.local.entity.AttendanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDao {
    @Query("SELECT a.* FROM attendance a INNER JOIN enrollments e ON a.enrollmentId = e.id WHERE e.studentId = :studentId ORDER BY a.date DESC")
    fun getByStudent(studentId: Long): Flow<List<AttendanceEntity>>

    @Query("SELECT a.* FROM attendance a INNER JOIN enrollments e ON a.enrollmentId = e.id WHERE e.batchId = :batchId ORDER BY a.date DESC")
    fun getByBatch(batchId: Long): Flow<List<AttendanceEntity>>

    @Query("SELECT * FROM attendance WHERE enrollmentId = :enrollmentId AND date = :date")
    suspend fun getByEnrollmentAndDate(enrollmentId: Long, date: Long): AttendanceEntity?

    @Query("SELECT * FROM attendance WHERE date BETWEEN :startDate AND :endDate")
    fun getByDateRange(startDate: Long, endDate: Long): Flow<List<AttendanceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(attendance: AttendanceEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(attendanceList: List<AttendanceEntity>)

    @Update
    suspend fun update(attendance: AttendanceEntity)

    @Query("DELETE FROM attendance WHERE id = :id")
    suspend fun delete(id: Long)
}
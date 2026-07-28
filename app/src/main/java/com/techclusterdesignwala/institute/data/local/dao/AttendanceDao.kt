package com.techclusterdesignwala.institute.data.local.dao

import androidx.room.*
import com.techclusterdesignwala.institute.data.local.entity.AttendanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDao {
    @Query("SELECT * FROM attendance WHERE studentId = :studentId ORDER BY date DESC")
    fun getByStudentId(studentId: Long): Flow<List<AttendanceEntity>>

    @Query("SELECT * FROM attendance WHERE studentId = :studentId AND subject = :subject")
    fun getByStudentAndSubject(studentId: Long, subject: String): Flow<List<AttendanceEntity>>

    @Query("SELECT * FROM attendance WHERE date BETWEEN :startDate AND :endDate")
    fun getByDateRange(startDate: Long, endDate: Long): Flow<List<AttendanceEntity>>

    @Query("SELECT * FROM attendance WHERE studentId = :studentId AND date BETWEEN :startDate AND :endDate")
    fun getByStudentAndDateRange(studentId: Long, startDate: Long, endDate: Long): Flow<List<AttendanceEntity>>

    @Query("SELECT COUNT(*) FROM attendance WHERE studentId = :studentId AND status = 'PRESENT'")
    fun getPresentCount(studentId: Long): Flow<Int>

    @Query("SELECT COUNT(*) FROM attendance WHERE studentId = :studentId")
    fun getTotalCount(studentId: Long): Flow<Int>

    @Query("SELECT COUNT(*) FROM attendance WHERE studentId = :studentId AND status = 'PRESENT' AND subject = :subject")
    fun getPresentCountBySubject(studentId: Long, subject: String): Flow<Int>

    @Query("SELECT COUNT(*) FROM attendance WHERE studentId = :studentId AND subject = :subject")
    fun getTotalCountBySubject(studentId: Long, subject: String): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(attendance: AttendanceEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(attendanceList: List<AttendanceEntity>)

    @Update
    suspend fun update(attendance: AttendanceEntity)

    @Query("DELETE FROM attendance WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT DISTINCT subject FROM attendance WHERE studentId = :studentId")
    fun getDistinctSubjects(studentId: Long): Flow<List<String>>
}

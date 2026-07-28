package com.techclusterdesignwala.institute.data.local.dao

import androidx.room.*
import com.techclusterdesignwala.institute.data.local.entity.ResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ResultDao {
    @Query("SELECT * FROM results WHERE studentRollNo = :rollNo ORDER BY semester DESC")
    fun getByStudent(rollNo: String): Flow<List<ResultEntity>>

    @Query("SELECT * FROM results WHERE studentRollNo = :rollNo AND semester = :semester")
    fun getByStudentAndSemester(rollNo: String, semester: Int): Flow<List<ResultEntity>>

    @Query("SELECT * FROM results WHERE semester = :semester")
    fun getBySemester(semester: Int): Flow<List<ResultEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(result: ResultEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(results: List<ResultEntity>)

    @Update
    suspend fun update(result: ResultEntity)

    @Delete
    suspend fun delete(result: ResultEntity)
}

package com.techclusterdesignwala.institute.data.local.dao

import androidx.room.*
import com.techclusterdesignwala.institute.data.local.entity.FacultyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FacultyDao {
    @Query("SELECT * FROM faculty ORDER BY name ASC")
    fun getAll(): Flow<List<FacultyEntity>>

    @Query("SELECT * FROM faculty WHERE department = :department ORDER BY name ASC")
    fun getByDepartment(department: String): Flow<List<FacultyEntity>>

    @Query("SELECT * FROM faculty WHERE id = :id")
    suspend fun getById(id: Long): FacultyEntity?

    @Query("SELECT * FROM faculty WHERE name LIKE '%' || :query || '%' OR department LIKE '%' || :query || '%' OR designation LIKE '%' || :query || '%'")
    fun search(query: String): Flow<List<FacultyEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(faculty: FacultyEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(facultyList: List<FacultyEntity>)

    @Update
    suspend fun update(faculty: FacultyEntity)

    @Delete
    suspend fun delete(faculty: FacultyEntity)
}

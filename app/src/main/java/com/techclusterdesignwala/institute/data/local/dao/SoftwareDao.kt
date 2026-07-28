package com.techclusterdesignwala.institute.data.local.dao

import androidx.room.*
import com.techclusterdesignwala.institute.data.local.entity.SoftwareEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SoftwareDao {
    @Query("SELECT * FROM software ORDER BY name ASC")
    fun getAll(): Flow<List<SoftwareEntity>>

    @Query("SELECT * FROM software WHERE id = :id")
    suspend fun getById(id: Long): SoftwareEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(software: SoftwareEntity): Long

    @Update
    suspend fun update(software: SoftwareEntity)

    @Delete
    suspend fun delete(software: SoftwareEntity)

    @Query("SELECT COUNT(*) FROM software")
    fun count(): Flow<Int>
}
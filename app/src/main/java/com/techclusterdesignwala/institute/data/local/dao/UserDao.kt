package com.techclusterdesignwala.institute.data.local.dao

import androidx.room.*
import com.techclusterdesignwala.institute.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE email = :email AND passwordHash = :passwordHash")
    suspend fun login(email: String, passwordHash: String): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getById(id: Long): UserEntity?

    @Query("SELECT * FROM users ORDER BY name ASC")
    fun getAll(): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE role = :role ORDER BY name ASC")
    fun getByRole(role: String): Flow<List<UserEntity>>

    @Query("SELECT COUNT(*) FROM users WHERE role = 'STUDENT'")
    fun getStudentCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM users WHERE role = 'TEACHER'")
    fun getTeacherCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity): Long

    @Update
    suspend fun update(user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)
}
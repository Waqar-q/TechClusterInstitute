package com.techclusterdesignwala.institute.data.repository

import com.techclusterdesignwala.institute.data.local.dao.*
import com.techclusterdesignwala.institute.data.local.entity.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdminRepository @Inject constructor(
    private val userDao: UserDao,
    private val softwareDao: SoftwareDao,
    private val batchDao: BatchDao,
    private val enrollmentDao: EnrollmentDao
) {
    fun getAllUsers(): Flow<List<UserEntity>> = userDao.getAll()
    fun getUsersByRole(role: String): Flow<List<UserEntity>> = userDao.getByRole(role)
    suspend fun getUserById(id: Long): UserEntity? = userDao.getById(id)
    suspend fun createUser(user: UserEntity): Long = userDao.insert(user)
    suspend fun updateUser(user: UserEntity) = userDao.update(user)
    suspend fun deleteUser(user: UserEntity) = userDao.delete(user)

    fun getAllSoftware(): Flow<List<SoftwareEntity>> = softwareDao.getAll()
    suspend fun getSoftwareById(id: Long): SoftwareEntity? = softwareDao.getById(id)
    suspend fun createSoftware(software: SoftwareEntity): Long = softwareDao.insert(software)
    suspend fun updateSoftware(software: SoftwareEntity) = softwareDao.update(software)

    fun getAllBatches(): Flow<List<BatchEntity>> = batchDao.getAll()
    suspend fun getBatchById(id: Long): BatchEntity? = batchDao.getById(id)
    suspend fun createBatch(batch: BatchEntity): Long = batchDao.insert(batch)
    suspend fun updateBatch(batch: BatchEntity) = batchDao.update(batch)

    fun getEnrollmentsByBatch(batchId: Long): Flow<List<EnrollmentEntity>> = enrollmentDao.getByBatch(batchId)
    suspend fun enrollStudent(studentId: Long, batchId: Long, date: Long = System.currentTimeMillis()): Long {
        return enrollmentDao.insert(EnrollmentEntity(studentId = studentId, batchId = batchId, enrollmentDate = date))
    }
    suspend fun removeEnrollment(id: Long) = enrollmentDao.updateStatus(id, "DROPPED")
}

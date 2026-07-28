package com.techclusterdesignwala.institute.data.repository

import com.techclusterdesignwala.institute.data.local.dao.AssignmentDao
import com.techclusterdesignwala.institute.data.local.entity.AssignmentEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AssignmentRepository @Inject constructor(
    private val assignmentDao: AssignmentDao
) {
    fun getAllAssignments(): Flow<List<AssignmentEntity>> = assignmentDao.getAll()

    fun getAssignmentsByStatus(status: String): Flow<List<AssignmentEntity>> =
        assignmentDao.getByStatus(status)

    suspend fun getAssignmentById(id: Long): AssignmentEntity? = assignmentDao.getById(id)

    fun getPendingCount(): Flow<Int> = assignmentDao.getPendingCount()

    suspend fun createAssignment(assignment: AssignmentEntity): Long =
        assignmentDao.insert(assignment)

    suspend fun updateAssignment(assignment: AssignmentEntity) =
        assignmentDao.update(assignment)

    suspend fun deleteAssignment(assignment: AssignmentEntity) =
        assignmentDao.delete(assignment)
}

package com.techclusterdesignwala.institute.data.repository

import com.techclusterdesignwala.institute.data.local.dao.FacultyDao
import com.techclusterdesignwala.institute.data.local.dao.StudentDao
import com.techclusterdesignwala.institute.data.local.entity.FacultyEntity
import com.techclusterdesignwala.institute.data.local.entity.StudentEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DirectoryRepository @Inject constructor(
    private val facultyDao: FacultyDao,
    private val studentDao: StudentDao
) {
    fun getAllFaculty(): Flow<List<FacultyEntity>> = facultyDao.getAll()

    fun getFacultyByDepartment(department: String): Flow<List<FacultyEntity>> =
        facultyDao.getByDepartment(department)

    suspend fun getFacultyById(id: Long): FacultyEntity? = facultyDao.getById(id)

    fun searchFaculty(query: String): Flow<List<FacultyEntity>> = facultyDao.search(query)

    fun getAllStudents(): Flow<List<StudentEntity>> = studentDao.getAll()

    suspend fun addFaculty(faculty: FacultyEntity): Long = facultyDao.insert(faculty)

    suspend fun addStudent(student: StudentEntity): Long = studentDao.insert(student)

    suspend fun updateFaculty(faculty: FacultyEntity) = facultyDao.update(faculty)

    suspend fun deleteFaculty(faculty: FacultyEntity) = facultyDao.delete(faculty)
}

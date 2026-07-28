package com.techclusterdesignwala.institute.data.repository

import com.techclusterdesignwala.institute.data.local.dao.ResultDao
import com.techclusterdesignwala.institute.data.local.entity.ResultEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResultRepository @Inject constructor(
    private val resultDao: ResultDao
) {
    fun getResultsByStudent(rollNo: String): Flow<List<ResultEntity>> =
        resultDao.getByStudent(rollNo)

    fun getResultsByStudentAndSemester(rollNo: String, semester: Int): Flow<List<ResultEntity>> =
        resultDao.getByStudentAndSemester(rollNo, semester)

    suspend fun addResult(result: ResultEntity): Long = resultDao.insert(result)

    suspend fun addResults(results: List<ResultEntity>) = resultDao.insertAll(results)

    suspend fun updateResult(result: ResultEntity) = resultDao.update(result)

    suspend fun deleteResult(result: ResultEntity) = resultDao.delete(result)
}

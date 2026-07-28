package com.techclusterdesignwala.institute.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "results")
data class ResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val studentRollNo: String,
    val subjectName: String,
    val internalMarks: Float? = null,
    val finalMarks: Float? = null,
    val totalMarks: Float? = null,
    val maxMarks: Float = 100f,
    val semester: Int,
    val examType: String,
    val grade: String? = null
)

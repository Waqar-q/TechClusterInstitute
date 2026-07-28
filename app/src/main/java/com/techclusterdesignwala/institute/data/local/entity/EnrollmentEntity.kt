package com.techclusterdesignwala.institute.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "enrollments")
data class EnrollmentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val studentId: Long,
    val batchId: Long,
    val enrollmentDate: Long,
    val status: String = "ACTIVE"
)
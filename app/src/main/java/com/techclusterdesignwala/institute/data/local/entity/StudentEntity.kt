package com.techclusterdesignwala.institute.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class StudentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val rollNo: String,
    val course: String,
    val semester: Int,
    val section: String,
    val email: String,
    val phone: String,
    val enrollmentYear: Int,
    val profileImageUri: String? = null
)

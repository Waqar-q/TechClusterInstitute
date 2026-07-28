package com.techclusterdesignwala.institute.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "faculty")
data class FacultyEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val department: String,
    val email: String,
    val phone: String,
    val cabin: String? = null,
    val designation: String,
    val specialization: String? = null,
    val profileImageUri: String? = null
)

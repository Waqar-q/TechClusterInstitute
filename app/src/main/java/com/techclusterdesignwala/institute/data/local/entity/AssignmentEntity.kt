package com.techclusterdesignwala.institute.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assignments")
data class AssignmentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val batchId: Long,
    val title: String,
    val description: String = "",
    val subjectName: String = "",
    val dueDate: Long,
    val assignedDate: Long,
    val status: String = "PENDING",
    val maxMarks: Float = 100f,
    val obtainedMarks: Float? = null,
    val teacherName: String = ""
)
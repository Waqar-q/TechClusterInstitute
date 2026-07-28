package com.techclusterdesignwala.institute.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assignment_submissions")
data class AssignmentSubmissionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val assignmentId: Long,
    val studentId: Long,
    val submittedDate: Long,
    val status: String = "SUBMITTED",
    val marks: Float? = null,
    val feedback: String? = null
)
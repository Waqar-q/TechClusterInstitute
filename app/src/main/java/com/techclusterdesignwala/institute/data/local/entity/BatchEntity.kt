package com.techclusterdesignwala.institute.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "batches")
data class BatchEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val softwareId: Long,
    val teacherId: Long,
    val startTime: String,
    val endTime: String,
    val daysOfWeek: String,
    val roomNo: String = "",
    val startDate: Long,
    val endDate: Long,
    val maxStudents: Int = 20
)
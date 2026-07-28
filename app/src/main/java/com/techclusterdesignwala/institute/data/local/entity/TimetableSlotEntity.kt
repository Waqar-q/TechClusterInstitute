package com.techclusterdesignwala.institute.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timetable_slots")
data class TimetableSlotEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dayOfWeek: Int,
    val subjectName: String,
    val teacherName: String,
    val startTime: String,
    val endTime: String,
    val roomNo: String,
    val course: String,
    val semester: Int,
    val section: String,
    val colorHex: String = "#203070"
)

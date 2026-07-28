package com.techclusterdesignwala.institute.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val date: Long,
    val startTime: String,
    val endTime: String? = null,
    val venue: String,
    val eventType: String,
    val organizer: String? = null,
    val isHoliday: Boolean = false
)

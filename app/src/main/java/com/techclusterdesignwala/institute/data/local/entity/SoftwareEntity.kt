package com.techclusterdesignwala.institute.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "software")
data class SoftwareEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val description: String = "",
    val durationHours: Int = 40
)
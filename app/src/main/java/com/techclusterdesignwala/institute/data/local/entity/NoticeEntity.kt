package com.techclusterdesignwala.institute.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notices")
data class NoticeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val content: String,
    val date: Long,
    val postedBy: String,
    val priority: String,
    val attachmentUri: String? = null,
    val expiryDate: Long? = null
)

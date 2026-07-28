package com.techclusterdesignwala.institute.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.techclusterdesignwala.institute.data.local.converter.Converters
import com.techclusterdesignwala.institute.data.local.dao.*
import com.techclusterdesignwala.institute.data.local.entity.*

@Database(
    entities = [
        StudentEntity::class,
        AttendanceEntity::class,
        TimetableSlotEntity::class,
        AssignmentEntity::class,
        ResultEntity::class,
        NoticeEntity::class,
        EventEntity::class,
        FacultyEntity::class,
        NotificationEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class InstituteDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
    abstract fun attendanceDao(): AttendanceDao
    abstract fun timetableSlotDao(): TimetableSlotDao
    abstract fun assignmentDao(): AssignmentDao
    abstract fun resultDao(): ResultDao
    abstract fun noticeDao(): NoticeDao
    abstract fun eventDao(): EventDao
    abstract fun facultyDao(): FacultyDao
    abstract fun notificationDao(): NotificationDao
}

package com.techclusterdesignwala.institute.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.techclusterdesignwala.institute.data.local.converter.Converters
import com.techclusterdesignwala.institute.data.local.dao.*
import com.techclusterdesignwala.institute.data.local.entity.*

@Database(
    entities = [
        UserEntity::class,
        SoftwareEntity::class,
        BatchEntity::class,
        EnrollmentEntity::class,
        AttendanceEntity::class,
        AssignmentEntity::class,
        AssignmentSubmissionEntity::class,
        ResultEntity::class,
        NoticeEntity::class,
        EventEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class InstituteDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun softwareDao(): SoftwareDao
    abstract fun batchDao(): BatchDao
    abstract fun enrollmentDao(): EnrollmentDao
    abstract fun attendanceDao(): AttendanceDao
    abstract fun assignmentDao(): AssignmentDao
    abstract fun assignmentSubmissionDao(): AssignmentSubmissionDao
    abstract fun resultDao(): ResultDao
    abstract fun noticeDao(): NoticeDao
    abstract fun eventDao(): EventDao
}